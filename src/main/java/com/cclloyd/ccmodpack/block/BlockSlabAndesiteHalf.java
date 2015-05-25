package com.cclloyd.ccmodpack.block;

import net.minecraft.block.material.Material;

import com.cclloyd.ccmodpack.CCModpack;

public class BlockSlabAndesiteHalf extends BlockSlabGeneric {
	
	public static final String name = "blockSlabAndesite";

	public BlockSlabAndesiteHalf() {
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