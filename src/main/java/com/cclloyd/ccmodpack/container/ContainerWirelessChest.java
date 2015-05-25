package com.cclloyd.ccmodpack.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.cclloyd.ccmodpack.tileentity.EntityWirelessChest;

public class ContainerWirelessChest extends Container {
	
	/**
	 * The container is used to link the client side gui to the server side inventory and it is where
	 * you add the slots to your gui. It can also be used to sync server side data with the client but
	 * that will be covered in a later tutorial
	 */
	
	// Stores a reference to the tile entity instance for later use
	public EntityWirelessChest wirelessChestEntity;

	
	// must assign a slot number to each of the slots used by the GUI.
	// For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
	// Each time we add a Slot to the container, it automatically increases the slotIndex, which means
	//  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
	//  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
	//  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)

	private final int HOTBAR_COUNT = 9;
	private final int PLAYER_ROWS = 3;
	private final int PLAYER_COLUMNS = 9;
	private final int PLAYER_SLOTS = PLAYER_COLUMNS * PLAYER_ROWS;
	private final int VANILLA_SLOTS = HOTBAR_COUNT + PLAYER_SLOTS;
	private final int VANILLA_START = 0;
	
	private final int TE_START = VANILLA_START + VANILLA_SLOTS;
	private final int TE_ROWS = 3;
	private final int TE_COLUMNS = 9;
	private final int TE_SLOTS = TE_ROWS * TE_COLUMNS;

		
	// These store cache values, used by the server to only update the client side tile entity when values have changed
	private int [] cachedFields;
		
	public void setFreq(int frequency) {
		this.wirelessChestEntity.setFreq(frequency);
	}
	
	public int getFreq() {
		return this.wirelessChestEntity.getFreq();
	}
	
	public String getFreqStr() {
		return this.wirelessChestEntity.getFreqStr();
	}
	
	public ContainerWirelessChest(InventoryPlayer invPlayer, EntityWirelessChest wirelessChestEntity) {
		this.wirelessChestEntity = wirelessChestEntity;
		
		//  Adds all the slots for items
		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 164;
		final int PLAYER_XPOS = 8;
		final int PLAYER_YPOS = 106;
		final int TE_XPOS = 8;
		final int TE_YPOS = 48;
		
		// Add the players hotbar to the gui - the [xpos, ypos] location of each item
		for (int x = 0; x < HOTBAR_COUNT; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
		}

		
		// Add the rest of the players inventory to the gui
		for (int y = 0; y < PLAYER_ROWS; y++) {
			for (int x = 0; x < PLAYER_COLUMNS; x++) {
				int slotNumber = HOTBAR_COUNT + y * PLAYER_COLUMNS + x;
				int xpos = PLAYER_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_YPOS + y * SLOT_Y_SPACING;
				addSlotToContainer(new Slot(invPlayer, slotNumber,  xpos, ypos));
			}
		}

		
		if (TE_SLOTS != wirelessChestEntity.getSizeInventory()) {
			System.err.println("Mismatched slot count in ContainerBasic(" + TE_SLOTS
												  + ") and TileInventory (" + wirelessChestEntity.getSizeInventory()+")");
		}
		
		
		// Add the tile inventory container to the gui
		for (int y = 0; y < TE_ROWS; y++) {
			for (int x = 0; x < TE_COLUMNS; x++) {
				int slotNumber = (y * HOTBAR_COUNT) + x;
				int xpos = TE_XPOS + (x * SLOT_X_SPACING);
				int ypos = TE_YPOS + (y * SLOT_Y_SPACING);
				addSlotToContainer(new Slot(wirelessChestEntity, slotNumber,  xpos, ypos));
			}
		}
		
	}

	// Vanilla calls this method every tick to make sure the player is still able to access the inventory, and if not closes the gui
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return wirelessChestEntity.isUseableByPlayer(player);
	}

	// This is where you specify what happens when a player shift clicks a slot in the gui
	//  (when you shift click a slot in the TileEntity Inventory, it moves it to the first available position in the hotbar and/or
	//    player inventory.  When you you shift-click a hotbar or player inventory item, it moves it to the first available
	//    position in the TileEntity inventory)
	// At the very least you must override this and return null or the game will crash when the player shift clicks a slot
	// returns null if the source slot is empty, or if none of the the source slot items could be moved
	//   otherwise, returns a copy of the source stack
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return null;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		// Check if the slot clicked is one of the vanilla container slots
		if (sourceSlotIndex >= VANILLA_START && sourceSlotIndex < VANILLA_START + VANILLA_SLOTS) {
			// This is a vanilla container slot so merge the stack into the tile inventory
			if (!mergeItemStack(sourceStack, TE_START, TE_START + TE_SLOTS, false)){
				return null;
			}
		} else if (sourceSlotIndex >= TE_START && sourceSlotIndex < TE_START + TE_SLOTS) {
			// This is a TE slot so merge the stack into the players inventory
			if (!mergeItemStack(sourceStack, VANILLA_START, VANILLA_START + VANILLA_SLOTS, false)) {
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

	// pass the close container message to the tileEntityInventory (not strictly needed for this example)
	//  see ContainerChest and TileEntityChest
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		
		super.onContainerClosed(playerIn);
		this.wirelessChestEntity.closeInventory(playerIn);
	}
	
	@Override
	public boolean enchantItem(EntityPlayer player, int action) {
		this.wirelessChestEntity.setFreq(action);
		return super.enchantItem(player, action);
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
		boolean fieldHasChanged [] = new boolean[wirelessChestEntity.getFieldCount()];
		if (cachedFields == null) {
			cachedFields = new int[wirelessChestEntity.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged || cachedFields[i] != wirelessChestEntity.getField(i)) {
				cachedFields[i] = wirelessChestEntity.getField(i);
				fieldHasChanged[i] = true;
			}
		}

		// go through the list of crafters (players using this container) and update them if necessary
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting)this.crafters.get(i);
			for (int fieldID = 0; fieldID < wirelessChestEntity.getFieldCount(); ++fieldID) {
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
		wirelessChestEntity.setField(id, data);
	}
	
}