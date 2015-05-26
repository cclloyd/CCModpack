package com.cclloyd.ccmodpack.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.cclloyd.ccmodpack.registry.BlockRegistry;

public class BlockSlabDioriteDouble extends BlockSlabDioriteHalf {

	public static final String name = "blockSlabDioriteDouble";
	
	public BlockSlabDioriteDouble() {
		super();
	}
	
	@Override
	public final boolean isDouble() {
		return true;
	}
	
	
	@Override 
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockRegistry.blockSlabDiorite);
    }
}
