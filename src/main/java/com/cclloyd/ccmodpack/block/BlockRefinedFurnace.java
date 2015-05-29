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
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.GuiHandlerRegistry;
import com.cclloyd.ccmodpack.tileentity.EntityRefinedFurnace;

public class BlockRefinedFurnace extends BlockContainer {

	public static final String name = "refinedFurnace";
    //public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyInteger BURNING_SIDES_COUNT = PropertyInteger.create("burning_sides_count", 0, 4);
    public static final PropertyDirection PROPERTYFACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
	// change the furnace emitted light ("block light") depending on how many slots are burning
	private static final int ONE_SIDE_LIGHT_VALUE = 15;  // light value for a single side burning
	private static final int FOUR_SIDE_LIGHT_VALUE = ONE_SIDE_LIGHT_VALUE; // light value for four sides burning

	
	public int getLightValue(IBlockAccess world, BlockPos pos) {
		int lightValue = 0;
		IBlockState blockState = getActualState(getDefaultState(), world, pos);
		int burningSides = (Integer)blockState.getValue(BURNING_SIDES_COUNT);

   	if (burningSides == 0) {
			lightValue = 0;
		} else {
			lightValue = 15;
			// linearly interpolate the light value depending on how many slots are burning
			//lightValue = ONE_SIDE_LIGHT_VALUE + (int)((FOUR_SIDE_LIGHT_VALUE - ONE_SIDE_LIGHT_VALUE) / (1.0 - 1.0) * burningSides);
			//lightValue = ONE_SIDE_LIGHT_VALUE + (int)((FOUR_SIDE_LIGHT_VALUE - ONE_SIDE_LIGHT_VALUE) / (1.0 - 1.0) * burningSides);
		}
		lightValue = MathHelper.clamp_int(lightValue, 0, FOUR_SIDE_LIGHT_VALUE);
		return lightValue;
	}
	
	
	public BlockRefinedFurnace() {
		super(Material.rock);
		setUnlocalizedName(CCModpack.MODID + "_" +  name);
        //this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setCreativeTab(CreativeTabs.tabDecorations);
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

	//------------------------------------------------------------
	// we will give our Block a property which tracks the number of burning sides, 0 - 4.
	// This will affect the appearance of the block model, but does not need to be stored in metadata (it's stored in
	//  the tileEntity) so we only need to implement getActualState.  getStateFromMeta, getMetaFromState aren't required
	//   but we give defaults anyway because the base class getMetaFromState gives an error if we don't

	// update the block state depending on the number of slots which contain burning fuel
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing facing = (EnumFacing)state.getValue(PROPERTYFACING);
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof EntityRefinedFurnace) {
			EntityRefinedFurnace EntityRefinedFurnace = (EntityRefinedFurnace)tileEntity;
			int burningSlots = EntityRefinedFurnace.numberOfBurningFuelSlots();
			burningSlots = MathHelper.clamp_int(burningSlots, 0, 1);
			return getDefaultState().withProperty(BURNING_SIDES_COUNT, burningSlots).withProperty(PROPERTYFACING, facing);
		}
		return state;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        
		int burningSlots = 0;
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof EntityRefinedFurnace) {
			EntityRefinedFurnace EntityRefinedFurnace = (EntityRefinedFurnace)tileEntity;
			burningSlots = EntityRefinedFurnace.numberOfBurningFuelSlots();
			burningSlots = MathHelper.clamp_int(burningSlots, 0, 1);
		}
		
		return this.getDefaultState().withProperty(BURNING_SIDES_COUNT, burningSlots).withProperty(PROPERTYFACING, placer.getHorizontalFacing().getOpposite());
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		EnumFacing facing = EnumFacing.getHorizontal(meta);
	    return this.getDefaultState().withProperty(BURNING_SIDES_COUNT, Integer.valueOf(meta)).withProperty(PROPERTYFACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		
		EnumFacing facing = (EnumFacing)state.getValue(PROPERTYFACING);
	    int facingbits = facing.getHorizontalIndex();
	    return facingbits;
	}

	// necessary to define which properties your blocks use
	// will also affect the variants listed in the blockstates model file.
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {BURNING_SIDES_COUNT, PROPERTYFACING});
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

	// render using a BakedModel
	@Override
	public int getRenderType() {
		return 3;
	}
}
