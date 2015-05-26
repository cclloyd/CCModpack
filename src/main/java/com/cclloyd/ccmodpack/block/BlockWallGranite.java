package com.cclloyd.ccmodpack.block;

import net.minecraft.block.BlockWall;
import net.minecraft.init.Blocks;

import com.cclloyd.ccmodpack.CCModpack;

public class BlockWallGranite extends BlockWall {

	public static final String name = "blockWallGranite";
	
	public BlockWallGranite() {
		super(Blocks.stone);
		
	}
	
	
	@Override
	public String getUnlocalizedName() {
		return CCModpack.MODID + "_" + name;
	}
	
}
