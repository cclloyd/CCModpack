package com.cclloyd.ccmodpack.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.cclloyd.ccmodpack.tileentity.EntityRefinedFurnace;

public class ContainerRefinedFurnace extends Container {

	// Stores the tile entity instance for later use
	private EntityRefinedFurnace entityRefinedFurnace;

	// These store cache values, used by the server to only update the client side tile entity when values have changed
	private int [] cachedFields;

	// must assign a slot index to each of the slots used by the GUI.
	// For this container, we can see the furnace fuel, input, and output slots as well as the player inventory slots and the hotbar.
	// Each time we add a Slot to the container using addSlotToContainer(), it automatically increases the slotIndex, which means
	//  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
	//  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
	//  36 - 39 = fuel slots (tileEntity 0 - 3)
	//  40 - 44 = input slots (tileEntity 4 - 8)
	//  45 - 49 = output slots (tileEntity 9 - 13)

	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	public final int FUEL_COUNT = 1;
	public final int INPUT_ROWS = 2;
	public final int INPUT_COLUMNS = 2;
	public final int INPUT_COUNT = INPUT_ROWS * INPUT_COLUMNS;
	public final int OUTPUT_ROWS = 2;
	public final int OUTPUT_COLUMNS = 2;
	public final int OUTPUT_COUNT = OUTPUT_ROWS * OUTPUT_COLUMNS;
	public final int FURNACE_COUNT = FUEL_COUNT + INPUT_ROWS * INPUT_COLUMNS + OUTPUT_ROWS * OUTPUT_COLUMNS;

	// slot index is the unique index for all slots in this container i.e. 0 - 35 for invPlayer then 36 - 49 for entityRefinedFurnace
	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int FIRST_FUEL_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int FIRST_INPUT_SLOT_INDEX = FIRST_FUEL_SLOT_INDEX + FUEL_COUNT;
	@SuppressWarnings("unused")
	private final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX + INPUT_COUNT;

	// slot number is the slot number within each component; i.e. invPlayer slots 0 - 35, and entityRefinedFurnace slots 0 - 14
	private final int FIRST_FUEL_SLOT_NUMBER = 0;
	private final int FIRST_INPUT_SLOT_NUMBER = FIRST_FUEL_SLOT_NUMBER + FUEL_COUNT;
	private final int FIRST_OUTPUT_SLOT_NUMBER = FIRST_INPUT_SLOT_NUMBER + INPUT_COUNT;

	public ContainerRefinedFurnace(InventoryPlayer invPlayer, EntityRefinedFurnace entityRefinedFurnace) {
		this.entityRefinedFurnace = entityRefinedFurnace;

		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 142;
		// Add the players hotbar to the gui - the [xpos, ypos] location of each item
		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
		}

		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOS = 84;
		// Add the rest of the players inventory to the gui
		for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
			for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
				int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
				addSlotToContainer(new Slot(invPlayer, slotNumber,  xpos, ypos));
			}
		}

		final int FUEL_SLOTS_XPOS = 80;
		final int FUEL_SLOTS_YPOS = 55;
		// Add the tile fuel slots
		for (int x = 0; x < FUEL_COUNT; x++) {
			int slotNumber = x + FIRST_FUEL_SLOT_NUMBER;
			addSlotToContainer(new SlotFuel(entityRefinedFurnace, slotNumber, FUEL_SLOTS_XPOS + SLOT_X_SPACING * x, FUEL_SLOTS_YPOS));
		}

		final int INPUT_XPOS = 26;
		final int INPUT_YPOS = 16;
		// Add the tile input slots
		for (int i = 0; i < INPUT_ROWS; i++) {
			for (int j = 0; j < INPUT_COLUMNS; j++) {
				int slotNumber = FIRST_INPUT_SLOT_NUMBER + i * INPUT_COLUMNS + j;
				int xpos = INPUT_XPOS + SLOT_X_SPACING * i;
				int ypos = INPUT_YPOS + SLOT_Y_SPACING * j;
				addSlotToContainer(new SlotSmeltableInput(entityRefinedFurnace, slotNumber, xpos, ypos));
			}
		}
		
		final int OUTPUT_XPOS = 116;
		final int OUTPUT_YPOS = 16;
		// Add the tile output slots
		for (int i = 0; i < OUTPUT_ROWS; i++) {
			for (int j = 0; j < OUTPUT_COLUMNS; j++) {
				int slotNumber = FIRST_OUTPUT_SLOT_NUMBER + i * OUTPUT_COLUMNS + j;
				int xpos = OUTPUT_XPOS + SLOT_X_SPACING * i;
				int ypos = OUTPUT_YPOS + SLOT_Y_SPACING * j;
				addSlotToContainer(new SlotOutput(entityRefinedFurnace, slotNumber, xpos, ypos));
			}
		}
	}

	// Checks each tick to make sure the player is still able to access the inventory and if not closes the gui
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return entityRefinedFurnace.isUseableByPlayer(player);
	}

	// This is where you specify what happens when a player shift clicks a slot in the gui
	//  (when you shift click a slot in the TileEntity Inventory, it moves it to the first available position in the hotbar and/or
	//    player inventory.  When you you shift-click a hotbar or player inventory item, it moves it to the first available
	//    position in the TileEntity inventory - either input or fuel as appropriate for the item you clicked)
	// At the very least you must override this and return null or the game will crash when the player shift clicks a slot
	// returns null if the source slot is empty, or if none of the source slot items could be moved.
	//   otherwise, returns a copy of the source stack
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return null;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		// Check if the slot clicked is one of the vanilla container slots
		if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			// This is a vanilla container slot so merge the stack into one of the furnace slots
			// If the stack is smeltable try to merge merge the stack into the input slots
			if (EntityRefinedFurnace.getSmeltingResultForItem(sourceStack) != null){
				if (!mergeItemStack(sourceStack, FIRST_INPUT_SLOT_INDEX, FIRST_INPUT_SLOT_INDEX + INPUT_COUNT, false)){
					return null;
				}
			}	else if (EntityRefinedFurnace.getItemBurnTime(sourceStack) > 0) {
				if (!mergeItemStack(sourceStack, FIRST_FUEL_SLOT_INDEX, FIRST_FUEL_SLOT_INDEX + FUEL_COUNT, true)) {
					// Setting the boolean to true places the stack in the bottom slot first
					return null;
				}
			}	else {
				return null;
			}
		} else if (sourceSlotIndex >= FIRST_FUEL_SLOT_INDEX && sourceSlotIndex < FIRST_FUEL_SLOT_INDEX + FURNACE_COUNT) {
			// This is a furnace slot so merge the stack into the players inventory: try the hotbar first and then the main inventory
			//   because the main inventory slots are immediately after the hotbar slots, we can just merge with a single call
			if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return null;
			}
		} else {
			System.err.print("Invalid slotIndex:" + sourceSlotIndex);
			return null;
		}

		// If stack size == 0 (the entire stack was moved) set slot contents to null
		if (sourceStack.stackSize == 0) {
			sourceSlot.putStack(null);
		} else {
			sourceSlot.onSlotChanged();
		}

		sourceSlot.onPickupFromSlot(player, sourceStack);
		return copyOfSourceStack;
	}

	/* Client Synchronization */

	// This is where you check if any values have changed and if so send an update to any clients accessing this container
	// The container itemstacks are tested in Container.detectAndSendChanges, so we don't need to do that
	// We iterate through all of the TileEntity Fields to find any which have changed, and send them.
	// You don't have to use fields if you don't wish to; just manually match the ID in sendProgressBarUpdate with the value in
	//   updateProgressBar()
	// The progress bar values are restricted to shorts.  If you have a larger value (eg int), it's not a good idea to try and split it
	//   up into two shorts because the progress bar values are sent independently, and unless you add synchronisation logic at the
	//   receiving side, your int value will be wrong until the second short arrives.  Use a custom packet instead.
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged [] = new boolean[entityRefinedFurnace.getFieldCount()];
		if (cachedFields == null) {
			cachedFields = new int[entityRefinedFurnace.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged || cachedFields[i] != entityRefinedFurnace.getField(i)) {
				cachedFields[i] = entityRefinedFurnace.getField(i);
				fieldHasChanged[i] = true;
			}
		}

		// go through the list of crafters (players using this container) and update them if necessary
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting)this.crafters.get(i);
			for (int fieldID = 0; fieldID < entityRefinedFurnace.getFieldCount(); ++fieldID) {
				if (fieldHasChanged[fieldID]) {
					// Note that although sendProgressBarUpdate takes 2 ints on a server these are truncated to shorts
					icrafting.sendProgressBarUpdate(this, fieldID, cachedFields[fieldID]);
				}
			}
		}
	}

	// Called when a progress bar update is received from the server. The two values (id and data) are the same two
	// values given to sendProgressBarUpdate.  In this case we are using fields so we just pass them to the tileEntity.
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		entityRefinedFurnace.setField(id, data);
	}

	// SlotFuel is a slot for fuel items
	public class SlotFuel extends Slot {
		public SlotFuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		// if this function returns false, the player won't be able to insert the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return EntityRefinedFurnace.isItemValidForFuelSlot(stack);
		}
	}

	// SlotSmeltableInput is a slot for input items
	public class SlotSmeltableInput extends Slot {
		public SlotSmeltableInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		// if this function returns false, the player won't be able to insert the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return EntityRefinedFurnace.isItemValidForInputSlot(stack);
		}
	}

	// SlotOutput is a slot that will not accept any items
	public class SlotOutput extends Slot {
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		// if this function returns false, the player won't be able to insert the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return EntityRefinedFurnace.isItemValidForOutputSlot(stack);
		}
	}
}