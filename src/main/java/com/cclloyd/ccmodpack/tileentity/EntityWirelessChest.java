package com.cclloyd.ccmodpack.tileentity;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class EntityWirelessChest extends TileEntity implements IInventory {
	
	/**
	 * This is a simple tile entity implementing IInventory that can store 9 item stacks
	 */
	// Create and initialize the items variable that will store store the items
	final int NUMBER_OF_SLOTS = 27;
	private ItemStack[] itemStacks = new ItemStack[NUMBER_OF_SLOTS];
	public final static String name = "wirelessChestEntity";
	
	public static int FIELD_ID_FREQUENCY = 0;
	private int frequency = 0;

	public EntityWirelessChest() {
		//this.frequency = 0;
	}
	
	public int getFreq() {
		return this.frequency;
	}
	
	public String getFreqStr() {
		return Integer.toString(this.frequency);
	}
	
	public void setFreq(int freq) {
		this.frequency = freq;
		markDirty();
	}
	
	/* The following are some IInventory methods you are required to override */

	// Gets the number of slots in the inventory
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	// Gets the stack in the given slot
	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return itemStacks[slotIndex];
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
	// 2) the player isn't too far away from the centre of the block
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj.getTileEntity(this.pos) != this) return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	// Return true if the given stack is allowed to go in the given slot.  In this case, we can insert anything.
	// This only affects things such as hoppers trying to insert items you need to use the container to enforce this for players
	// inserting items via the gui
	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
		return true;
	}

	// This is where you save any data that you don't want to lose when the tile entity unloads
	// In this case, it saves the itemstacks stored in the container
	@Override
	public void writeToNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save and load the tileEntity's location

		// to use an analogy with Java, this code generates an array of hashmaps
		// The itemStack in each slot is converted to an NBTTagCompound, which is effectively a hashmap of key->value pairs such
		//   as slot=1, id=2353, count=1, etc
		// Each of these NBTTagCompound are then inserted into NBTTagList, which is similar to an array.
		parentNBTTagCompound.setInteger("ChestFrequency", this.frequency);

		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (this.itemStacks[i] != null)	{
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		parentNBTTagCompound.setTag("Items", dataForAllSlots);
		//WirelessChestData data = new WirelessChestData("chestfreq"+frequency);
		//data.writeToNBT(parentNBTTagCompound);
		//worldObj.getMapStorage().setData("chestfreq"+frequency, data);
		//WorldSavedData data = new WorldSavedData(worldObj.getUniqueDataId(name));
		//data.writeToNBT(parentNBTTagCompound);
		//data = worldObj.getMapStorage().loadData(WorldSavedData.class, "chestfreq"+frequency);
		//worldObj.getMapStorage().setData("chestfreq"+frequency, data);
	}

	
	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound); // The super call is required to save and load the tiles location
		final byte NBT_TYPE_COMPOUND = 10;       // See NBTBase.createNewByType() for a listing
		//NBTTagList dataForAllSlots = parentNBTTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);
		String freqnbt = "InvWirelessChestFreq" + frequency;
		NBTTagList dataForAllSlots = parentNBTTagCompound.getTagList(freqnbt, NBT_TYPE_COMPOUND);

		frequency = parentNBTTagCompound.getInteger("ChestFrequency");

		//WorldSavedData data;
		//data = worldObj.getMapStorage().loadData(WorldSavedData.class, "chestfreq"+frequency);
		
		
		Arrays.fill(itemStacks, null);           // set all slots to empty
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			int slotIndex = dataForOneSlot.getByte("Slot") & 255;

			if (slotIndex >= 0 && slotIndex < this.itemStacks.length) {
				this.itemStacks[slotIndex] = ItemStack.loadItemStackFromNBT(dataForOneSlot);
			}
		}
	}

	// set all slots to empty
	@Override
	public void clear() {
		Arrays.fill(itemStacks, null);
	}

	// will add a key for this container to the lang file so we can name it in the GUI
	@Override
	public String getName() {
		return "container.wirelessChestEntity.name";
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

	// -----------------------------------------------------------------------------------------------------------
	// The following methods are not needed for this example but are part of IInventory so they must be implemented

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

	@Override
	public int getField(int id) {
		if (id == FIELD_ID_FREQUENCY)
			return frequency;
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (id == FIELD_ID_FREQUENCY)
			frequency = value;
	}

	@Override
	public int getFieldCount() {
		return 1;
	}
}