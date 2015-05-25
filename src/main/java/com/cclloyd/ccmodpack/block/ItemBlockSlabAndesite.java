package com.cclloyd.ccmodpack.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;

public class ItemBlockSlabAndesite extends ItemSlab {

	/**
     * Initializes a new instance of the ItemBlockStainedBrickSlab class.
     * @param block the block behind the item.
     * @param slab the half height slab.
     * @param doubleSlab the full height slab.
     * @param stacked whether or not the block is the stacked version.
     */
	
	public ItemBlockSlabAndesite (Block block, BlockSlabAndesiteHalf slab, BlockSlabAndesiteDouble fullslab, Boolean stacked) {
        super(block, slab, fullslab);
	}
	
}
