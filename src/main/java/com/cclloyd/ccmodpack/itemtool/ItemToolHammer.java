package com.cclloyd.ccmodpack.itemtool;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.cclloyd.ccmodpack.BlockRegistry;
import com.google.common.collect.Sets;

 /**
 * ItemToolsTest is a simple tool used to test the interactions between tools and blocks
 * For background information see here
 * http://greyminecraftcoder.blogspot.ch/2015/01/mining-blocks-with-tools.html
 *
 * Manipulating the item properties or methods is generally the best way to customise mining behaviour if you are creating
 * your own custom item.
 *
 * Item.setHarvestLevel(ToolClass, level) - first choice - give your tool one or more ToolClasses equivalent to existing tool eg "pickaxe", "axe".
   ItemTool constructor -
     EFFECTIVE_ON set of blocks - to add extra "special case" blocks that your item is effective on which don't match the Item's ToolClass(es).
     ToolMaterial, which affects the maximum dig speed and the tool durability.
   Item.getStrVsBlock() - add further "special cases" that ToolClass and ItemTool constructor don't cover.
   Item.getDigSpeed() - metadata / damage sensitive version of getStrVsBlock()
   Item.onBlockStartBreak() - can be used to abort block breaking before it is destroyed
   Item.onBlockDestroyed() - used to damage the item once the block is destroyed

   In Adventure mode, the "CanDestroy" NBT tag in the tool's itemstack is used to determine which blocks it can destroy.
 */

public class ItemToolHammer extends ItemTool {
	
	public static final String name = "itemToolHammer";
	
	static float attackDamage;// = 3.0F;
	static ToolMaterial material;// = Item.ToolMaterial.EMERALD;
	
	@SuppressWarnings("rawtypes")
	static Set effectiveBlocks = Sets.newHashSet(new Block[] {Blocks.stone, Blocks.sand, Blocks.cobblestone, Blocks.brick_block});
	  
	public ItemToolHammer(float attackDamage, ToolMaterial material) { 
		super(attackDamage, material, effectiveBlocks);
		//setUnlocalizedName(CCModpack.MODID + "_" +  name);
		final int WOOD_HARDNESS_LEVEL = 0;
		final int STONE_HARDNESS_LEVEL = 1;
		this.setHarvestLevel("axe", WOOD_HARDNESS_LEVEL);  // default.  can also be set when creating the block instance, which is typically what vanilla does
		this.setHarvestLevel("pickaxe", STONE_HARDNESS_LEVEL);  // can add hardness level for as many or few ToolClasses as you want; new ToolClasses also possible
  }

  @Override
  public float getStrVsBlock(ItemStack stack, Block block) {
    Float result = super.getStrVsBlock(stack, block);
    return result;
  }

  @Override
  public float getDigSpeed(ItemStack stack, IBlockState state) {
    Float result = super.getDigSpeed(stack, state);
    return result;
  }

  	@Override
  	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
  		Boolean result = true;
  		result = super.onBlockStartBreak(itemstack, pos, player);
  		return result;
  	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
		Boolean result = true;
		
		if(!worldIn.isRemote) {
			// Stone/Cobblestone > Gravel
			if (blockIn == Blocks.stone)
				spawnDifferentBlock(Blocks.gravel, worldIn, pos);
			else if (blockIn == Blocks.cobblestone)
				spawnDifferentBlock(Blocks.gravel, worldIn, pos);
			
			// Gravel > Sand
			else if (blockIn == Blocks.gravel)
				spawnDifferentBlock(Blocks.sand, worldIn, pos);
			
			// Sand > Dust
			else if (blockIn == Blocks.sand)
				spawnDifferentBlock(BlockRegistry.blockDust, worldIn, pos);
			
			// Brick Block > Bricks
			else if (blockIn == Blocks.brick_block)
				spawnDifferentBlockMulti(Items.brick, 4, worldIn, pos);
			
			// Stone brick > Cracked Stone Brick
			else if (blockIn == Blocks.stonebrick)
				spawnDifferentBlockMeta(Blocks.stonebrick, 2, worldIn, pos);
			
			// Netherrack > Soul Sand
			else if (blockIn == Blocks.netherrack)
				spawnDifferentBlock(Blocks.soul_sand, worldIn, pos);
			
			// Redstone block > redstone
			else if (blockIn == Blocks.redstone_block)
				spawnDifferentBlockMulti(Items.redstone, 9, worldIn, pos);
			
			// Lapis block > lapis
			else if (blockIn == Blocks.lapis_block)
				spawnDifferentBlockMultiMeta(Items.dye, 9, 4, worldIn, pos);
			
			
			// No special case
			else
				result = super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
		}
		else
			result = super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);

		
		return result;
	}

	
	private void spawnDifferentBlock(Block newBlock, World worldIn, BlockPos pos) {
		worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(newBlock, 1)));
		worldIn.destroyBlock(pos, false);
	}
	
	private void spawnDifferentBlockMeta(Block newBlock, int meta, World worldIn, BlockPos pos) {
		worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(newBlock, 1, meta)));
		worldIn.destroyBlock(pos, false);
	}
	
	@SuppressWarnings("unused")
	private void spawnDifferentBlock(Item newItem, World worldIn, BlockPos pos) {
		worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(newItem, 1)));
		worldIn.destroyBlock(pos, false);
	}
	
	private void spawnDifferentBlockMulti(Item newItem, int multi, World worldIn, BlockPos pos) {
		worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(newItem, multi)));
		worldIn.destroyBlock(pos, false);
	}
	
	private void spawnDifferentBlockMultiMeta(Item newItem, int multi, int meta, World worldIn, BlockPos pos) {
		worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(newItem, multi, meta)));
		worldIn.destroyBlock(pos, false);
	}

//  @Override
//  public boolean canHarvestBlock(Block blockIn) {
//    Startup.methodCallLogger.enterMethod("ItemToolsTest.canHarvestBlock#1", blockIn.getLocalizedName());
//    Boolean result = super.canHarvestBlock(blockIn);
//    Startup.methodCallLogger.exitMethod("ItemToolsTest.canHarvestBlock#1", result.toString());
//    return result;
//  }
//
//  @Override
//  public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
//    Startup.methodCallLogger.enterMethod("ItemToolsTest.canHarvestBlock#2", par1Block.getLocalizedName() + ", " + itemStack.getDisplayName());
//    Boolean result = super.canHarvestBlock(par1Block, itemStack);
//    Startup.methodCallLogger.exitMethod("ItemToolsTest.canHarvestBlock#2", result.toString());
//    return result;
//  }
//
//  @Override
//  public int getHarvestLevel(ItemStack stack, String toolClass) {
//    Startup.methodCallLogger.enterMethod("ItemToolsTest.getHarvestLevel", stack.getDisplayName() + ", " + toolClass);
//    Integer result = super.getHarvestLevel(stack, toolClass);
//    Startup.methodCallLogger.exitMethod("ItemToolsTest.getHarvestLevel", result.toString());
//    return result;
//  }
}