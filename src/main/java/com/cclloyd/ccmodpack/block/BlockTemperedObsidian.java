package com.cclloyd.ccmodpack.block;

import net.minecraft.block.BlockObsidian;
import net.minecraftforge.common.ForgeHooks;

import com.cclloyd.ccmodpack.CCModpack;

public class BlockTemperedObsidian extends BlockObsidian {

	public static final String name = "blockTemperedObsidian";
	
	public BlockTemperedObsidian() {
		super();
	}
	
	@Override
	public String getUnlocalizedName() {
		return CCModpack.MODID + "_" + name;
	}
}
