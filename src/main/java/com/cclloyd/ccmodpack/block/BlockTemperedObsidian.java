package com.cclloyd.ccmodpack.block;

import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;

import com.cclloyd.ccmodpack.CCModpack;

public class BlockTemperedObsidian extends BlockObsidian {

	public static final String name = "blockTemperedObsidian";
	
	public BlockTemperedObsidian() {
		super();
		setUnlocalizedName(CCModpack.MODID + "_" + name);
		setResistance(6000);
		setHarvestLevel("pickaxe", 3);
		setHardness(50);
		setStepSound(Blocks.obsidian.stepSound);
	}
	
	@Override
	public String getUnlocalizedName() {
		return CCModpack.MODID + "_" + name;
	}
}
