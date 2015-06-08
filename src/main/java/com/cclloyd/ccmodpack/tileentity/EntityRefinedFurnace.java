package com.cclloyd.ccmodpack.tileentity;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;

import com.cclloyd.ccmodpack.registry.ForgeRecipe;
import com.cclloyd.ccmodpack.registry.ForgeRecipes;

/**
 * TileInventorySmelting is an advanced sided inventory that works like a vanilla furnace except that it has 5 input and output slots,
 * 4 fuel slots and cooks at up to four times the speed.
 * The input slots are used sequentially rather than in parallel, i.e. the first slot cooks, then the second, then the third, etc
 * The fuel slots are used in parallel.  The more slots burning in parallel, the faster the cook time.
 * The code is heavily based on TileEntityFurnace.
 */

/*
 * TODO
 * Make obsidian smelt into tempered obsidian (check input blocks and change output blocks
 * Add model for smelting lava
 * Add smelting 2 items together to make single item
 * Make obsidian smelt time longer
 * 
 */
public class EntityRefinedFurnace extends TileEntity implements IInventory, IUpdatePlayerListBox {
	
	public static final String name = "entityRefinedFurnace";
	
	// Create and initialize the itemStacks variable that will store store the itemStacks
	public static final int FUEL_SLOTS_COUNT = 1;
	public static final int INPUT_SLOTS_COUNT = 4;
	public static final int OUTPUT_SLOTS_COUNT = 4;
	public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

	public static final int FUEL_SLOT = 0;
	public static final int FIRST_INPUT_SLOT = FUEL_SLOT + FUEL_SLOTS_COUNT;
	public static final int FIRST_OUTPUT_SLOT = FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT;
	
	private ItemStack[] itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
	/** Is the furnace currently burning */
	private boolean burning;
	/** The number of burn ticks remaining on the current piece of fuel */
	private int burnTimeRemaining;
	/** The initial fuel value of the currently burning fuel (in ticks of burn duration) */
	private int burnTimeInitialValue;
	/** The number of ticks the current item has been cooking*/
	private short cookTime;
	/** The number of ticks the current custom item has been cooking*/
	private short customCookTime = 0;
	/** The number of ticks required to cook an item*/
	private static final short COOK_TIME_FOR_COMPLETION = 200;  // vanilla value is 200 = 10 seconds
	private static final short LAVA_COOK_TIME = 150;
	private static final short CUSTOM_COOK_TIME = 300;
	/** If the furnace has lava as fuel */
	private boolean hasLava = false;
	/** Cache burning to keep burning when nothing to smelt */
	private boolean cachedBurning = false;
	/** The custom recipe currently in the furnace */
	private ForgeRecipe custom = null;
	
	/**
	 * Returns weather or not the furnace is burning lava
	 * @return has lava
	 */
	public boolean hasLava() {
		return hasLava;
	}
	
	/**
	 * Returns the amount of fuel remaining on the currently burning item in the given fuel slot.
	 * @fuelSlot the number of the fuel slot (0..1)
	 * @return fraction remaining, between 0 - 1
	 */
	public double fractionOfFuelRemaining() {
		if (burnTimeInitialValue <= 0 ) 
			return 0;
		
		double fraction = burnTimeRemaining / (double)burnTimeInitialValue;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}
	
	/**
	 * return the remaining burn time of the fuel in the given slot
	 * @return seconds remaining
	 */
	public int secondsOfFuelRemaining() {
		if (burnTimeRemaining <= 0) 
			return 0;
		return burnTimeRemaining / 20;
	}
	
	/**
	 * returns if the furnace is burning or not
	 * @return furnace lit
	 */
	public boolean isBurning() {
		return (burnTimeRemaining > 0);
	}

	/**
	 * Returns the amount of cook time completed on the currently cooking item.
	 * @return fraction remaining, between 0 - 1
	 */
	public double fractionOfCookTimeComplete() {
		double fraction;
		if (hasLava)
			if (custom != null)
				fraction = cookTime / (double)CUSTOM_COOK_TIME;
			else
				fraction = cookTime / (double)LAVA_COOK_TIME;
		else
			fraction = cookTime / (double)COOK_TIME_FOR_COMPLETION;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}

	// This method is called every tick to update the tile entity, i.e.
	// - see if the fuel has run out, and if so turn the furnace "off" and slowly uncook the current item (if any)
	// - see if any of the items have finished smelting
	// It runs both on the server and the client.
	@Override
	public void update() {
		// Burns fuel to nothing when lit and not smelting
		burnFuel();
		
		// If not empty
		if (!inputEmpty()) {
			if (hasLava) {
				custom = findCustomRecipe();
				if (custom != null) {
					updateCustom();
				}
				else {
					if (customCookTime > 0)
						customCookTime = 0;
					updateNormal();
				}
			}
			else
				updateNormal();
		}
		else {
			if (cookTime > 0) cookTime = 0;
		}

		// when the number of burning slots changes, we need to force the block to re-render, otherwise the change in
		//   state will not be visible.  Likewise, we need to force a lighting recalculation.
		// The block update (for renderer) is only required on client side, but the lighting is required on both, since
		//    the client needs it for rendering and the server needs it for crop growth etc
		if (burning == cachedBurning) {
			if (worldObj.isRemote)
				worldObj.markBlockForUpdate(pos);
			worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos);
		}
		
		markDirty();
	}
	
	private void updateNormal() {
		// Not Custom Recipe
		
		// If there is nothing to smelt or there is no room in the output, reset cookTime and return
		if (canSmelt()) {
			// Only start burning when there are items to smelt
			// If fuel is available, keep cooking the item, otherwise start "uncooking" it at double speed
			if (burnTimeRemaining > 0)
				cookTime += 1;
			else 
				cookTime -= 2;

			if (cookTime < 0) 
				cookTime = 0;
			
			if (hasLava) {
				// If cookTime has reached maxCookTime smelt the item and reset cookTime
				if (cookTime >= LAVA_COOK_TIME) {
					smeltItem();
					cookTime = 0;
				}
			}
			else {
				if (cookTime >= COOK_TIME_FOR_COMPLETION) {
					smeltItem();
					cookTime = 0;
				}
			}
		}
		else {
			cookTime = 0;
		}
		// end normal smelt check
	}
	
	
	private void updateCustom() {
		boolean stillCustom = false;
		ForgeRecipe newCustom;
		
		for (int i=0; i<ForgeRecipes.recipeCount; i++) {
			newCustom = ForgeRecipes.getRecipe(i);
			if (newCustom.isSameRecipe(custom)) {
				stillCustom = true; 
				break;
			}
		}
		
		if (!stillCustom) {
			custom = null;	
			cookTime = 0;
		}
		else {
			if (canOutputCustomRecipe()) {
				// If fuel is available, keep cooking the item, otherwise start "uncooking" it at double speed
				if (burnTimeRemaining > 0) cookTime += 1;
				else cookTime -= 2;
				if (cookTime < 0) cookTime = 0;
				
				if (burnTimeRemaining > 0) customCookTime += 1;
				else customCookTime -= 2;
				if (customCookTime < 0) customCookTime = 0;
				
				
				// If item done cooking
				if (cookTime >= custom.cookTime()) {
					if (custom == null)
						custom = findCustomRecipe();
					if (custom != null) {
						outputCustomItem();
						decreaseInputStacks();
					}
					cookTime = 0;
					custom = null;
				}
			}
		}
		
	}
	
	
	/**
	 * Checks if the current custom recipe can be output or not (slots not full)
	 * @return If it can be output
	 */
	private boolean canOutputCustomRecipe() {
		if (custom == null) return false;
		Item output = custom.output();
		
		if (output == null) return false;
		// Iterate through output slots
		for (int outputSlot=FIRST_OUTPUT_SLOT; outputSlot< FIRST_OUTPUT_SLOT+OUTPUT_SLOTS_COUNT; outputSlot++) {
			// If slot is empty, it can be put there
			if (itemStacks[outputSlot] == null)
				return true;
			// If stack is same item as recipe output
			if (itemStacks[outputSlot].getItem() == output)
				// If current stack size + output quantity is less than max stack size of that item
				if (itemStacks[outputSlot].stackSize + custom.quantity() < itemStacks[outputSlot].getMaxStackSize())
					return true;
		}
		
		return false;
	}
	
	
	/**
	 * Cycles through the recipe list and checks if the input slots contain a custom recipe.
	 * @return The custom recipe found, if any (null if otherwise)
	 */
	private ForgeRecipe findCustomRecipe() {
		//boolean[] inputFound;
		boolean[] recipeInputFound;
		boolean [] inputSlotUsed;
		for (int i=0; i<ForgeRecipes.recipeCount; i++) {
			//inputFound = new boolean[4];
			recipeInputFound = new boolean[4];
			inputSlotUsed = new boolean[4];
			ForgeRecipe recipe = ForgeRecipes.getRecipe(i);
			/*
			 * keep track of 
			 * -recipe item # found
			 * -slot recipe item # found
			 * -if recipe item # found
			 */
			//System.out.println("Checking for recipe " + recipe.name() + ".");
			
			// If recipe is only 1 item/block
			if (recipe.inputCount() == 1) {
				//System.out.println("Checking single item recipe.");
				// For each input slot
				for (int inputSlot=FIRST_INPUT_SLOT; inputSlot<INPUT_SLOTS_COUNT+FIRST_INPUT_SLOT; inputSlot++) {
					//System.out.println("inputSlot=" + inputSlot);
					if (itemStacks[inputSlot] != null)
						// If input slot is same as recipe input
						if (itemStacks[inputSlot].getItem() == recipe.getInput(0)) {
							//System.out.println("Item in input same as single recipe input");
							return recipe;
						}
				}
			}
			
			
			
			
			else if (recipe.inputCount() == 2) {
				// Mark null inputs found
				recipeInputFound[2] = true; recipeInputFound[3] = true;
				//System.out.println("Checking double-item recipe");
				
				// For each recipe input
				for (int j = 0; j<recipe.inputCount(); j++) {
					// If recipe input not null
					if (recipe.getInput(j) != null)
						// For each input slot
						for (int inputSlot=FIRST_INPUT_SLOT; inputSlot<INPUT_SLOTS_COUNT+FIRST_INPUT_SLOT; inputSlot++) {
							// If the input slot isn't already used
							if (!inputSlotUsed[inputSlot-FIRST_INPUT_SLOT])
								// If the input slot isn't null too
								if (itemStacks[inputSlot] != null)
									// If the input equals recipe input
									if (itemStacks[inputSlot].getItem() == recipe.getInput(j))
										// If recipe input not already found
										if (!recipeInputFound[j]) {
											recipeInputFound[j] = true;
											inputSlotUsed[inputSlot-FIRST_INPUT_SLOT] = true;
											//System.out.println("Input slot " + (inputSlot - FIRST_INPUT_SLOT) + " used for item " + recipe.getInput(j));
										}
						}
				}
			}
			
			
			
			// If is an actual recipe
			else {
				//System.out.println("Checking multi-item recipe");
				// For each recipe input
				for (int j = 0; j<INPUT_SLOTS_COUNT; j++) {
					// If recipe input not null
					if (recipe.getInput(j) != null) {
						// For each input slot
						for (int inputSlot=FIRST_INPUT_SLOT; inputSlot<INPUT_SLOTS_COUNT+FIRST_INPUT_SLOT; inputSlot++) {
							// If the input slot isn't already used
							if (!inputSlotUsed[inputSlot-FIRST_INPUT_SLOT])
								// If the input slot isn't null too
								if (itemStacks[inputSlot] != null)
									// If the input equals recipe input
									if (itemStacks[inputSlot].getItem() == recipe.getInput(j))
										// If recipe input not already found
										if (!recipeInputFound[j]) {
											recipeInputFound[j] = true;
											inputSlotUsed[inputSlot-FIRST_INPUT_SLOT] = true;
											//System.out.println("Input slot " + (inputSlot - FIRST_INPUT_SLOT) + " used for item " + recipe.getInput(j));
										}
						}
					}
					// If recipe input is null
					else {
						// For each input slot
						for (int inputSlot=FIRST_INPUT_SLOT; inputSlot<INPUT_SLOTS_COUNT; inputSlot++) {
							// If the input slot isn't used
							if (!inputSlotUsed[inputSlot-FIRST_INPUT_SLOT])
								// If input slot is also null
								if (itemStacks[inputSlot] == null) {
									recipeInputFound[j] = true;
									inputSlotUsed[inputSlot-FIRST_INPUT_SLOT] = true;
								}
						}
					}
				}
			}
			if (recipeInputFound[0] && recipeInputFound[1] && recipeInputFound[2] && recipeInputFound[3])
				return recipe;
			
			
		}
		return null;
			
	}

	
	/**
	 * Decreases the stack size of all input stacks by one.  Used for custom recipes.
	 */
	private void decreaseInputStacks() {
		// If recipe is only 1 item, multiple slots can be full of same item
		if (custom.inputCount() == 1) {
			for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT; inputSlot++)	{
				if (itemStacks[inputSlot] != null)
					if (itemStacks[inputSlot].getItem() == custom.getInput(0)) {
						--itemStacks[inputSlot].stackSize;
						if (itemStacks[inputSlot].stackSize <= 0) {
							itemStacks[inputSlot] = null;
						}
						break;
					}
			}
		}
		
		// If recipe is 2 items, decrease only 2 stacks
		else if (custom.inputCount() == 2) {
			boolean[] inputUsed = {false, false};
			// For each input slot
			for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT; inputSlot++)	{
				//System.out.println("Inputs used: " + inputUsed[0] + "," + inputUsed[1]);
				
				// If input slot not null
				if (itemStacks[inputSlot] != null) {
					// If same as first recipe input
					if (itemStacks[inputSlot].getItem() == custom.getInput(0))
						// If recipe input not already used
						if (!inputUsed[0]) {
							//System.out.println("Decreating stack of " + custom.getInput(0).getUnlocalizedName());
							--itemStacks[inputSlot].stackSize;
							inputUsed[0] = true;
						}
					// If same as second recipe input
					if (itemStacks[inputSlot].getItem() == custom.getInput(1))
						// If recipe input not already used
						if (!inputUsed[1]) {
							//System.out.println("Decreating stack of " + custom.getInput(1).getUnlocalizedName());
							--itemStacks[inputSlot].stackSize;
							inputUsed[1] = true;
						}
					if (itemStacks[inputSlot].stackSize <= 0)
						itemStacks[inputSlot] = null;
				}
			}
		}
		
		// If is actual recipe, decrease all stacks since they are all part of recipe or null
		else {
			for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT; inputSlot++)	{
				if (itemStacks[inputSlot] != null) {
					--itemStacks[inputSlot].stackSize;
					if (itemStacks[inputSlot].stackSize == 0)
						itemStacks[inputSlot] = null;
				}
			}
		}
	}
	
	
	/**
	 * Outputs a custom recipe to the slots
	 * @param recipe Recipe to be output
	 */
	private void outputCustomItem() {
		// Find itemstack that's identical to output item and not full
		
		// For each output slot
		for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT; outputSlot++)	{
			
			// If slot is empty
			if (itemStacks[outputSlot] == null) {
				// Make output slot new stack of recipe output
				itemStacks[outputSlot] = new ItemStack(custom.output(), custom.quantity());
				break;
			}
			
			// If slot isn't empty
			else {
				// If If output slot is same as recipe output
				if (itemStacks[outputSlot].getItem() == custom.output())
					// If current stackSize plus output quantity is less than max stack size
					if (itemStacks[outputSlot].stackSize + custom.quantity() <= itemStacks[outputSlot].getMaxStackSize())
						if (itemStacks[outputSlot].stackSize < itemStacks[outputSlot].getMaxStackSize()) {
							itemStacks[outputSlot].stackSize = itemStacks[outputSlot].stackSize + custom.quantity();
							break;
						}
			}
		}
		
		
		cookTime = 0;
	}

	
	/**
	 * Returns if all input slots are empty or not
	 * @return is empty
	 */
	private boolean inputEmpty() {
		boolean empty = true;
		for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT; inputSlot++)	{
			if (itemStacks[inputSlot] != null)
				empty = false;
		}
		return empty;
	}

	
	/**
	 * 	for each fuel slot: decreases the burn time, checks if burnTimeRemaining = 0 and tries to consume a new piece of fuel if one is available
	 * @return is currently burning
	 */
	private boolean burnFuel() {
		boolean inventoryChanged = false;
		
		if (burnTimeRemaining > 0) 
			--burnTimeRemaining;
		// If there isn't fuel left
		else if (burnTimeRemaining == 0) {
			custom = findCustomRecipe();
			// If something normal can smelt or a custom recipe can smelt then replace fuel
			if (canSmelt() || (canOutputCustomRecipe() && (custom != null)))
				if (itemStacks[FUEL_SLOT] != null && getItemBurnTime(itemStacks[FUEL_SLOT]) > 0) {
					hasLava = false;
					if (itemStacks[FUEL_SLOT].getItem() == Items.lava_bucket)
						hasLava = true;
	
					// If the stack in this slot is not null and is fuel, set burnTimeRemaining & burnTimeInitialValue to the
					// item's burn time and decrease the stack size
					burnTimeRemaining = burnTimeInitialValue = getItemBurnTime(itemStacks[FUEL_SLOT]);
					--itemStacks[FUEL_SLOT].stackSize;
					inventoryChanged = true;
					// If the stack size now equals 0 set the slot contents to the items container item. This is for fuel
					// items such as lava buckets so that the bucket is not consumed. If the item dose not have
					// a container item getContainerItem returns null which sets the slot contents to null
					if (itemStacks[FUEL_SLOT].stackSize == 0)
						itemStacks[FUEL_SLOT] = itemStacks[FUEL_SLOT].getItem().getContainerItem(itemStacks[FUEL_SLOT]);
				}
		}
		
		if (inventoryChanged) 
			markDirty();
		
		return true;
	}
	

	/**
	 * Check if any of the input items are smeltable and there is sufficient space in the output slots
	 * @return true if smelting is possible
	 */
	private boolean canSmelt() {
		return smeltItem(false);
	}

	/**
	 * Smelt an input item into an output slot, if possible
	 */
	private void smeltItem() {
		smeltItem(true);
	}

	/**
	 * checks that there is an item to be smelted in one of the input slots and that there is room for the result in the output slots
	 * If desired, performs the smelt
	 * @param performSmelt if true, perform the smelt.  if false, check whether smelting is possible, but don't change the inventory
	 * @return false if no items can be smelted, true otherwise
	 */
	private boolean smeltItem(boolean performSmelt) {
		Integer firstSuitableInputSlot = null;
		Integer firstSuitableOutputSlot = null;
		ItemStack result = null;
		// finds the first input slot which is smeltable and whose result fits into an output slot (stacking if possible)

		for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT; inputSlot++)	{
			if (itemStacks[inputSlot] != null) {
				result = getSmeltingResultForItem(itemStacks[inputSlot]);
				if (result != null) {
					// find the first suitable output slot- either empty, or with identical item that has enough space
					for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT; outputSlot++) {
						ItemStack outputStack = itemStacks[outputSlot];
						if (outputStack == null) {
							firstSuitableInputSlot = inputSlot;
							firstSuitableOutputSlot = outputSlot;
							break;
						}

						if (outputStack.getItem() == result.getItem() && (!outputStack.getHasSubtypes() || outputStack.getMetadata() == outputStack.getMetadata())
										&& ItemStack.areItemStackTagsEqual(outputStack, result)) {
							int combinedSize = itemStacks[outputSlot].stackSize + result.stackSize;
							if (combinedSize <= getInventoryStackLimit() && combinedSize <= itemStacks[outputSlot].getMaxStackSize()) {
								firstSuitableInputSlot = inputSlot;
								firstSuitableOutputSlot = outputSlot;
								break;
							}
						}
					}
					if (firstSuitableInputSlot != null) {
						break;
					}
				}
			}
		}
		
		if (firstSuitableInputSlot == null) 
			return false;
		if (!performSmelt)  {
			return true;
		}
		
		// alter input and output
		itemStacks[firstSuitableInputSlot].stackSize--;
		if (itemStacks[firstSuitableInputSlot].stackSize <= 0) 
			itemStacks[firstSuitableInputSlot] = null;
		
		if (itemStacks[firstSuitableOutputSlot] == null) {
			itemStacks[firstSuitableOutputSlot] = result.copy(); // Use deep .copy() to avoid altering the recipe
		} 
		else {
			itemStacks[firstSuitableOutputSlot].stackSize += result.stackSize;
		}
		
		markDirty();
		return true;
	}

	// returns the smelting result for the given stack. Returns null if the given stack can not be smelted
	public static ItemStack getSmeltingResultForItem(ItemStack stack) { 
		return FurnaceRecipes.instance().getSmeltingResult(stack); 
	}

	// returns the number of ticks the given item will burn. Returns 0 if the given item is not a valid fuel
	public static short getItemBurnTime(ItemStack stack) {
		int burntime = TileEntityFurnace.getItemBurnTime(stack);  // just use the vanilla values
		return (short)MathHelper.clamp_int(burntime, 0, Short.MAX_VALUE);
	}

	// Gets the number of slots in the inventory
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	// Gets the stack in the given slot
	@Override
	public ItemStack getStackInSlot(int i) {
		return itemStacks[i];
	}

	/**
	 * Removes some of the units from itemstack in the given slot, and returns as a separate itemstack
	 * @param slotIndex the slot number to remove the items from
	 * @param count the number of units to remove
	 * @return a new itemstack containing the units removed from the slot
	 */
	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot == null) return null;

		ItemStack itemStackRemoved;
		if (itemStackInSlot.stackSize <= count) {
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, null);
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.stackSize == 0) {
				setInventorySlotContents(slotIndex, null);
			}
		}
		markDirty();
		return itemStackRemoved;
	}

	// overwrites the stack in the given slotIndex with the given stack
	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
		itemStacks[slotIndex] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	// This is the maximum number if items allowed in each slot
	// This only affects things such as hoppers trying to insert items you need to use the container to enforce this for players
	// inserting items via the gui
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	// Return true if the given player is able to use this block. In this case it checks that
	// 1) the world tileentity hasn't been replaced in the meantime, and
	// 2) the player isn't too far away from the center of the block
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj.getTileEntity(this.pos) != this) 
			return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
	static public boolean isItemValidForFuelSlot(ItemStack itemStack)
	{
		return true;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	// Unlike the vanilla furnace, we allow anything to be placed in the input slots
	static public boolean isItemValidForInputSlot(ItemStack itemStack)
	{
		return true;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	static public boolean isItemValidForOutputSlot(ItemStack itemStack)
	{
		return false;
	}

	//------------------------------

	// This is where you save any data that you don't want to lose when the tile entity unloads
	// In this case, it saves the state of the furnace (burn time etc) and the itemstacks stored in the fuel, input, and output slots
	@Override
	public void writeToNBT(NBTTagCompound parentNBTTagCompound) {
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save and load the tiles location

		// Save the stored item stacks

		// to use an analogy with Java, this code generates an array of hashmaps
		// The itemStack in each slot is converted to an NBTTagCompound, which is effectively a hashmap of key->value pairs such
		//   as slot=1, id=2353, count=1, etc
		// Each of these NBTTagCompound are then inserted into NBTTagList, which is similar to an array.
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (this.itemStacks[i] != null) {
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		// the array of hashmaps is then inserted into the parent hashmap for the container
		parentNBTTagCompound.setTag("Items", dataForAllSlots);

		// Save everything else
		parentNBTTagCompound.setShort("CookTime", cookTime);
		parentNBTTagCompound.setTag("burnTimeRemaining", new NBTTagInt(burnTimeRemaining));
		parentNBTTagCompound.setTag("burnTimeInitial", new NBTTagInt(burnTimeInitialValue));
		parentNBTTagCompound.setBoolean("hasLava", hasLava);
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound); // The super call is required to save and load the tiles location
		final byte NBT_TYPE_COMPOUND = 10;       // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = nbtTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);

		Arrays.fill(itemStacks, null);           // set all slots to empty
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			byte slotNumber = dataForOneSlot.getByte("Slot");
			if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
				this.itemStacks[slotNumber] = ItemStack.loadItemStackFromNBT(dataForOneSlot);
			}
		}

		// Load everything else.  Trim the arrays (or pad with 0) to make sure they have the correct number of elements
		cookTime = nbtTagCompound.getShort("CookTime");
		burnTimeRemaining = nbtTagCompound.getInteger("burnTimeRemaining");
		burnTimeInitialValue = nbtTagCompound.getInteger("burnTimeInitial");
		hasLava = nbtTagCompound.getBoolean("hasLava");
		cachedBurning = burning;
	}

	// When the world loads from disk, the server needs to send the TileEntity information to the client
	//  it uses getDescriptionPacket() and onDataPacket() to do this
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		final int METADATA = 0;
		return new S35PacketUpdateTileEntity(this.pos, METADATA, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	//------------------------

	// set all slots to empty
	@Override
	public void clear() {
		Arrays.fill(itemStacks, null);
	}

	// will add a key for this container to the lang file so we can name it in the GUI
	@Override
	public String getName() {
		return "container." + name + ".name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	// standard code to look up what the human-readable name is
	@Override
	public IChatComponent getDisplayName() {
		return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
	}

	// Fields are used to send non-inventory information from the server to interested clients
	// The container code caches the fields and sends the client any fields which have changed.
	// The field ID is limited to byte, and the field value is limited to short. (if you use more than this, they get cast down
	//   in the network packets)
	// If you need more than this, or shorts are too small, use a custom packet in your container instead.

	private static final byte COOK_FIELD_ID = 0;
	private static final byte BURN_TIME_REMAINING_ID = 1;
	private static final byte BURN_TIME_INITIAL_ID = BURN_TIME_REMAINING_ID + (byte)FUEL_SLOTS_COUNT;
	private static final byte NUMBER_OF_FIELDS = BURN_TIME_INITIAL_ID + (byte)FUEL_SLOTS_COUNT;

	@Override
	public int getField(int id) {
		if (id == COOK_FIELD_ID) return cookTime;
		if (id >= BURN_TIME_REMAINING_ID && id < BURN_TIME_REMAINING_ID + FUEL_SLOTS_COUNT) {
			//return burnTimeRemaining[id - BURN_TIME_REMAINING_ID];
			return burnTimeRemaining;
		}
		if (id >= BURN_TIME_INITIAL_ID && id < BURN_TIME_INITIAL_ID + FUEL_SLOTS_COUNT) {
			//return burnTimeInitialValue[id - BURN_TIME_INITIAL_ID];
			return burnTimeInitialValue;
		}
		System.err.println("Invalid field ID in TileInventorySmelting.getField:" + id);
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		if (id == COOK_FIELD_ID)
			cookTime = (short)value;
		else if (id >= BURN_TIME_REMAINING_ID && id < BURN_TIME_REMAINING_ID + FUEL_SLOTS_COUNT)
			burnTimeRemaining = value;
		else if (id >= BURN_TIME_INITIAL_ID && id < BURN_TIME_INITIAL_ID + FUEL_SLOTS_COUNT)
			burnTimeInitialValue = value;
		else
			System.err.println("Invalid field ID in TileInventorySmelting.setField:" + id);
	}

	@Override
	public int getFieldCount() {
		return NUMBER_OF_FIELDS;
	}

	// -----------------------------------------------------------------------------------------------------------
	// The following methods are not needed for this example but are part of IInventory so they must be implemented

	// Unused unless your container specifically uses it.
	// Return true if the given stack is allowed to go in the given slot
	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
		return false;
	}

	/**
	 * This method removes the entire contents of the given slot and returns it.
	 * Used by containers such as crafting tables which return any items in their slots when you close the GUI
	 * @param slotIndex
	 * @return
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null) setInventorySlotContents(slotIndex, null);
		return itemStack;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

}