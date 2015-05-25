package com.cclloyd.ccmodpack.block;

import com.cclloyd.ccmodpack.CCModpack;

import net.minecraft.block.BlockStone;

public class BlockGranite extends BlockStone {

	public static final String name = "blockGranite";
	private BlockStone block = new BlockStone();
	
	public BlockGranite() {
		super();
		setCreativeTab(null);
	}
	
	@Override
	public String getUnlocalizedName() {
		return CCModpack.MODID + "_" + name;
	}
}
