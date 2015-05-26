package com.cclloyd.ccmodpack.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.cclloyd.ccmodpack.registry.BlockRegistry;

public class BlockSlabAndesiteDouble extends BlockSlabAndesiteHalf {

	public static final String name = "blockSlabAndesiteDouble";
	
	public BlockSlabAndesiteDouble() {
		super();
	}
	
	@Override
	public final boolean isDouble() {
		return true;
	}
	
	
	@Override 
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockRegistry.blockSlabAndesite);
    }
}
