package com.cclloyd.ccmodpack.block;

import net.minecraft.block.material.Material;

import com.cclloyd.ccmodpack.CCModpack;

public class BlockSlabGraniteHalf extends BlockSlabGeneric {
	
	public static final String name = "blockSlabGranite";

	public BlockSlabGraniteHalf() {
		super(Material.rock);
		setUnlocalizedName(CCModpack.MODID + "_" + name);
	}

	@Override
	public boolean isDouble() {
		return false;
	}
	
	@Override
	public String getUnlocalizedName(int meta) {
		return CCModpack.MODID + "_" +  name;
	}

}