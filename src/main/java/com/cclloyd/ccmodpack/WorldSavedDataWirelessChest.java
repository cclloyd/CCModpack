package com.cclloyd.ccmodpack;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class WorldSavedDataWirelessChest extends WorldSavedData {

	//Map<String, Integer> aMap = new HashMap<String, Integer>();
	//aMap.put("keystr" , 1);
	
	public Map<String, ItemStack[]> wirelessContents = new HashMap<String, ItemStack[]>();

	public WorldSavedDataWirelessChest(String mapName, Map<String, ItemStack[]> wirelessContents) {
		super(mapName);
		this.wirelessContents = wirelessContents;
	}
	
	public static WorldSavedDataWirelessChest forWorld(World worldIn) {
		MapStorage storage = worldIn.getPerWorldStorage();
		WorldSavedDataWirelessChest result = (WorldSavedDataWirelessChest)storage.loadData(WorldSavedDataWirelessChest.class, "frequencies");
		return result;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
	}
}
