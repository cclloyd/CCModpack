package com.cclloyd.ccmodpack.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.GuiHandlerRegistry;
import com.cclloyd.ccmodpack.tileentity.EntityRefinedFurnace;

public class BlockRefinedFurnace extends BlockContainer {

	public static final String name = "blockRefinedFurnace";
	
    //public static final PropertyBool PROPERTYBURNING = PropertyBool.create("burning");
    public static final PropertyInteger PROPERTYBURNING = PropertyInteger.create("burning", 0, 2);	
    //public static final PropertyBool PROPERTYLAVA = PropertyBool.create("lava");
    public static final PropertyDirection PROPERTYFACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	/** is the furnace currently lit */
    private boolean burning = false;
	private int burnLevel = 0;
	
	public BlockRefinedFurnace() {
		super(Material.rock);
		setUnlocalizedName(CCModpack.MODID + "_" +  name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PROPERTYBURNING, 0).withProperty(PROPERTYFACING, EnumFacing.NORTH));
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	
	public int getLightValue(IBlockAccess world, BlockPos pos) {
		this.burning = false;
		IBlockState blockState = getActualState(getDefaultState(), world, pos);
		if ((Integer)blockState.getValue(PROPERTYBURNING) > 0)
			burning = true;
		
		return burning ? 15 : 0;
	}
	
	public int getBurnLevel(EntityRefinedFurnace entity) {
		boolean isBurning = entity.isBurning();
		boolean isBurningLava = entity.hasLava();
		burnLevel = isBurning ? 1 : 0;
		burnLevel = isBurningLava ? 2 : burnLevel;
		return burnLevel;
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new EntityRefinedFurnace();
	}
	
	
	// Called when the block is right clicked
	// In this block it is used to open the blocks gui when right clicked by a player
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		// Uses the gui handler registered to your mod to open the gui for the given gui id
		// open on the server side only  (not sure why you shouldn't open client side too... vanilla doesn't, so we better not either)
		if (worldIn.isRemote) return true;

		playerIn.openGui(CCModpack.instance, GuiHandlerRegistry.GUI_REFINED_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	// This is where you can do something when the block is broken. In this case drop the inventory's contents
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileEntity);
		}

		// Super MUST be called last because it removes the tile entity
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing facing = (EnumFacing)state.getValue(PROPERTYFACING);
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof EntityRefinedFurnace) {
			EntityRefinedFurnace entityRefinedFurnace = (EntityRefinedFurnace)tileEntity;
			burnLevel = getBurnLevel(entityRefinedFurnace);
			return getDefaultState().withProperty(PROPERTYBURNING, burnLevel).withProperty(PROPERTYFACING, facing);
		}
		return state;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof EntityRefinedFurnace) {
			EntityRefinedFurnace entityRefinedFurnace = (EntityRefinedFurnace)tileEntity;
			burnLevel = getBurnLevel(entityRefinedFurnace);
		}
		
		return this.getDefaultState().withProperty(PROPERTYBURNING, burnLevel).withProperty(PROPERTYFACING, placer.getHorizontalFacing().getOpposite());
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		EnumFacing facing = EnumFacing.getHorizontal(meta);
	    return this.getDefaultState().withProperty(PROPERTYBURNING, burnLevel).withProperty(PROPERTYFACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		
		EnumFacing facing = (EnumFacing)state.getValue(PROPERTYFACING);
	    return facing.getHorizontalIndex();
	}

	// necessary to define which properties your blocks use
	// will also affect the variants listed in the blockstates model file.
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {PROPERTYBURNING, PROPERTYFACING});
	}


	// the block will render in the SOLID layer.
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.SOLID;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public boolean isFullCube() {
		return true;
	}

	@Override
	public int getRenderType() {
		return 3;
	}
}
