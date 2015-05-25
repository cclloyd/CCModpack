package com.cclloyd.ccmodpack.block;

import com.cclloyd.ccmodpack.CCModpack;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPoweredRedstoneLantern extends Block {

	public final static String name = "poweredRedstoneLantern";
	
	public BlockPoweredRedstoneLantern() {
		super(Material.rock);
		setUnlocalizedName(CCModpack.MODID + "_" +  name);
		setCreativeTab(CreativeTabs.tabRedstone);
		setLightLevel(1.0F);
	}

}
