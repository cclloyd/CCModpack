package com.cclloyd.ccmodpack.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import com.cclloyd.ccmodpack.CCModpack;

public abstract class BlockSlabGeneric extends BlockSlab {

	public static String name = "blockSlabGeneric";
	private static final PropertyBool VARIANT_PROPERTY = PropertyBool.create("variant");
    final int HALF_META_BIT = 8;
    
	public BlockSlabGeneric(Material material) {
		super(material);
		
		if (!this.isDouble())
            setCreativeTab(CreativeTabs.tabBlock);

        IBlockState blockState = this.blockState.getBaseState();
        blockState = blockState.withProperty(VARIANT_PROPERTY, false);
        if (!this.isDouble())
            blockState = blockState.withProperty(HALF, EnumBlockHalf.BOTTOM);
        
        this.useNeighborBrightness = !this.isDouble();

        setHardness(1.5F);
        setResistance(30);
        setDefaultState(blockState);
        
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return CCModpack.MODID + "_" +  name;
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public IProperty getVariantProperty() {
		return VARIANT_PROPERTY;
	}

	@Override
	public Object getVariant(ItemStack stack) {
		return false;
	}

	
	/**
     * Gets a block state from metadata.
     * @param meta the metadata or color value.
     * @return a block state with the meta encoded as the variant property.
     */
    @Override
    public final IBlockState getStateFromMeta(final int meta) {
        IBlockState blockState = this.getDefaultState();
        blockState = blockState.withProperty(VARIANT_PROPERTY, false);
        if (!this.isDouble()) {
            EnumBlockHalf value = EnumBlockHalf.BOTTOM;
            if ((meta & HALF_META_BIT) != 0)
                value = EnumBlockHalf.TOP;

            blockState = blockState.withProperty(HALF, value);
        }

        return blockState;
    }
    
    
    /**
     * Gets the metadata value from a block state.
     * @param state the block state.
     * @return the metadata or color value.
     */
    @Override
    public final int getMetaFromState(final IBlockState state) {
        if (this.isDouble())
           return 0;

        if ((EnumBlockHalf) state.getValue(HALF) == EnumBlockHalf.TOP) 
            return HALF_META_BIT;
        else
            return 0;
        
    }
    
    
    /**
     * Creates the block state object.
     * @return the block state with properties defined.
     */
    @Override
    protected final BlockState createBlockState() {
        if (this.isDouble())
            return new BlockState(this, new IProperty[] {VARIANT_PROPERTY});
        else
            return new BlockState(this, new IProperty[] {VARIANT_PROPERTY, HALF});
    }
    
    
}
