package com.cclloyd.ccmodpack.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;

public class BlockBorderlessGlass extends BlockGlass {

	public static final String name = "blockBorderlessGlass";

	public BlockBorderlessGlass() {
		//BlockGlass.
		super(Material.glass, true);
		setUnlocalizedName("minecraft:glass");
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
}
