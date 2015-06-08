package com.cclloyd.ccmodpack.block;

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.registry.CConstants;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.IFuelHandler;

public class BlockCharcoal extends Block implements IFuelHandler {

	public final static String name = "blockCharcoal";
	@SuppressWarnings("unused")
	private static String uname = "testtesttest";
	
	public BlockCharcoal() {
		super(Material.rock);
		//setUnlocalizedName(uname);
		setUnlocalizedName(CCModpack.MODID + "_" +  name);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		return 16000;
	}
	
	
	public static void config(Configuration config) {
		uname = config.getString("uname", "registry blocks", "testtesttest", "appa");
	}
}
