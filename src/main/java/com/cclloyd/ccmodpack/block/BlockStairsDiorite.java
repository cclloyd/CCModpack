/**
 * Copyright (C) Jon Rowlett. All rights reserved.
 */
package com.cclloyd.ccmodpack.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import com.cclloyd.ccmodpack.CCModpack;

public class BlockStairsDiorite extends BlockStairs {

    public static final String name = "blockStairsDiorite";

    public BlockStairsDiorite() {
        super(getBlockState(Blocks.stone, 4));
        setUnlocalizedName(CCModpack.MODID + "_" + name);
        useNeighborBrightness = true;
    }
    
    private static IBlockState getBlockState(Block block, int meta) {
        return block.getStateFromMeta(meta);
    }
}