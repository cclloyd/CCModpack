package com.cclloyd.ccmodpack.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.registry.BlockRegistry;

public class BlockCleanGlassPane extends BlockPane {

	public static final String name = "blockCleanGlassPane";
	public static final String name_tempered = "blockCleanGlassPaneTempered";
    private boolean ignoreSimilarity = true;
    
	public BlockCleanGlassPane(boolean tempered) {
		super(Material.glass, true);
		if (tempered) {
			setUnlocalizedName(CCModpack.MODID + "_" + name_tempered);
			setHardness(1.5F);
			setResistance(20F);
		}
		else {
			setUnlocalizedName(CCModpack.MODID + "_" + name);
			setHardness(0.3F);
			setResistance(1.5F);
		}
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (this == BlockRegistry.cleanGlass || this == BlockRegistry.cleanGlassPane || this == BlockRegistry.cleanGlassTempered || this == BlockRegistry.cleanGlassPaneTempered) {
            if (worldIn.getBlockState(pos.offset(side.getOpposite())) != iblockstate)
                return true;

            if (block == this)
                return false;
        }

        return !this.ignoreSimilarity && block == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
    }
	
}
