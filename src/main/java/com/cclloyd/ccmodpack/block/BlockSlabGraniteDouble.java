package com.cclloyd.ccmodpack.block;

import java.util.Random;

import com.cclloyd.ccmodpack.registry.BlockRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockSlabGraniteDouble extends BlockSlabGraniteHalf {

	public static final String name = "blockSlabGraniteDouble";
	
	public BlockSlabGraniteDouble() {
		super();
	}
	
	@Override
	public final boolean isDouble() {
		return true;
	}
	
	@Override 
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockRegistry.blockSlabGranite);
    }
}
