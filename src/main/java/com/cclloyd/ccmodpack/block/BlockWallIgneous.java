package com.cclloyd.ccmodpack.block;

import java.util.List;

import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.cclloyd.ccmodpack.CCModpack;

public class BlockWallIgneous extends BlockWall {

	public static final String name = "blockWallIgneous";
	
	//private static final PropertyBool VARIANT = PropertyBool.create("variant");
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockWallIgneous.EnumType.class);

    final int HALF_META_BIT = 8;
    
	public BlockWallIgneous() {
		super(Blocks.stone);
        setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(VARIANT, BlockWallIgneous.EnumType.NORMAL));
		
		//IBlockState blockState = this.blockState.getBaseState();
	    //blockState = blockState.withProperty(VARIANT, false);
	}
	
	@Override
	public String getUnlocalizedName() {
		return CCModpack.MODID + "_" + name;
	}

	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        BlockWallIgneous.EnumType[] aenumtype = BlockWallIgneous.EnumType.values();
        int i = aenumtype.length;

        for (int j = 0; j < i; ++j) {
        	System.out.println("i = " + i);
            BlockWallIgneous.EnumType enumtype = aenumtype[j];
            list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
        }
    }

    /**
     * Get the damage value that this Block should drop
     */
    @Override
    public int damageDropped(IBlockState state) {
        return ((BlockWallIgneous.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.DOWN ? super.shouldSideBeRendered(worldIn, pos, side) : true;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, BlockWallIgneous.EnumType.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return ((BlockWallIgneous.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(UP, Boolean.valueOf(!worldIn.isAirBlock(pos.up()))).withProperty(NORTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.north()))).withProperty(EAST, Boolean.valueOf(this.canConnectTo(worldIn, pos.east()))).withProperty(SOUTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.south()))).withProperty(WEST, Boolean.valueOf(this.canConnectTo(worldIn, pos.west())));
    }
    
    

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {UP, NORTH, EAST, WEST, SOUTH, VARIANT});
    }

    public static enum EnumType implements IStringSerializable {
        NORMAL(0, "stone", "normal"),
        GRANITE(1, "stone_granite", "granite"),
        SMOOTHGRANITE(2, "stone_granite_smooth", "smooth_granite"),
        DIORITE(3, "stone_diorite", "diorite"),
        SMOOTHDIORITE(4, "stone_diorite_smooth", "smooth_diorite"),
        ANDESITE(5, "stone_andesite", "andesite"),
        SMOOTHANDESITE(6, "stone_andesite_smooth", "smooth_andesite");
        
        private static final BlockWallIgneous.EnumType[] META_LOOKUP = new BlockWallIgneous.EnumType[values().length];
        private final int meta;
        private final String name;
        private String unlocalizedName;

        private EnumType(int meta, String name, String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMetadata() {
            return this.meta;
        }

        public String toString() {
            return this.name;
        }

        public static BlockWallIgneous.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length)
                meta = 0;

            return META_LOOKUP[meta];
        }

        public String getName() {
            return this.name;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        static {
            BlockWallIgneous.EnumType[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                BlockWallIgneous.EnumType var3 = var0[var2];
                META_LOOKUP[var3.getMetadata()] = var3;
            }
        }
    }
}
