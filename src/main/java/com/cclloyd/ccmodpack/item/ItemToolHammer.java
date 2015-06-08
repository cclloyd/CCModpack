package com.cclloyd.ccmodpack.item;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
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

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.registry.*;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

 /**
 * Item.setHarvestLevel(ToolClass, level) - first choice - give your tool one or more ToolClasses equivalent to existing tool eg "pickaxe", "axe".
   ItemTool constructor -
     EFFECTIVE_ON set of blocks - to add extra "special case" blocks that your item is effective on which don't match the Item's ToolClass(es).
     ToolMaterial, which affects the maximum dig speed and the tool durability.
   Item.getStrVsBlock() - add further "special cases" that ToolClass and ItemTool constructor don't cover.
   Item.getDigSpeed() - metadata / damage sensitive version of getStrVsBlock()
   Item.onBlockStartBreak() - can be used to abort block breaking before it is destroyed
   Item.onBlockDestroyed() - used to damage the item once the block is destroyed
 */

public class ItemToolHammer extends ItemTool {
	
	public static final String name = "itemToolHammer";
	public static final String name_diamond = "itemToolHammerDiamond";
	public static final String name_gold = "itemToolHammerGold";
	public static final String name_iron = "itemToolHammerIron";
	public static final String name_stone = "itemToolHammerStone";
	public static final String name_wood = "itemToolHammerWood";
	public static final String name_obsidian = "itemToolHammerObsidian";
	public static final String name_obsidian_reinforced = "itemToolHammerObsidianReinforced";
	
	/** The attack damage of the tool */
	public float attackDamage;
	/** The material of the tool */
	public ToolMaterial material;
	/** The set of blocks the tool is effective on */
	public static Set<Block> effectiveBlocks = Sets.newHashSet(new Block[] {Blocks.stone, Blocks.sand, Blocks.cobblestone, Blocks.brick_block});
	  
	/**
	 * Constructor for hammer
	 * @param attackDamage The attack damage multiplier
	 * @param material The tool material
	 */
	public ItemToolHammer(float attackDamage, ToolMaterial material) {
		super(attackDamage, material, effectiveBlocks);
		setName(material);
		setHarvestLevel(material);
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
	    return ImmutableSet.of("pickaxe", "spade", "axe");
	}
	
	private void setHarvestLevel(ToolMaterial material) {
		int harvestLevel;
		
		if (material == ToolMaterial.EMERALD)
			harvestLevel = 3;
		else if (material == ToolMaterial.GOLD)
			harvestLevel = 0;
		else if (material == ToolMaterial.IRON)
			harvestLevel = 2;
		else if (material == ToolMaterial.STONE)
			harvestLevel = 1;
		else if (material == ToolMaterial.WOOD)
			harvestLevel = 0;
		else if (material == ItemRegistry.toolObsidian)
			harvestLevel = 2;
		else if (material == ItemRegistry.toolObsidianReinforced)
			harvestLevel = 3;
		else
			harvestLevel = 0;
		
		
		this.setHarvestLevel("pickaxe", harvestLevel);
		this.setHarvestLevel("axe", harvestLevel);
		this.setHarvestLevel("spade", harvestLevel);
	}
	
	/**
	 * Used to set the unlocalized name based on the material fed to the constructor
	 * @param material
	 */
	private void setName(ToolMaterial material) {
		if (material == ToolMaterial.EMERALD)
			setUnlocalizedName(CCModpack.MODID + "_" +  name_diamond);
		else if (material == ToolMaterial.GOLD)
			setUnlocalizedName(CCModpack.MODID + "_" +  name_gold);
		else if (material == ToolMaterial.IRON)
			setUnlocalizedName(CCModpack.MODID + "_" +  name_iron);
		else if (material == ToolMaterial.STONE)
			setUnlocalizedName(CCModpack.MODID + "_" +  name_stone);
		else if (material == ToolMaterial.WOOD)
			setUnlocalizedName(CCModpack.MODID + "_" +  name_wood);
		else if (material == ItemRegistry.toolObsidian)
			setUnlocalizedName(CCModpack.MODID + "_" +  name_obsidian);
		else if (material == ItemRegistry.toolObsidianReinforced)
			setUnlocalizedName(CCModpack.MODID + "_" +  name_obsidian_reinforced);
		else
			setUnlocalizedName(CCModpack.MODID + "_" +  name);			
	}
	
	public String getName() {
		return getUnlocalizedName();
	}
	
	/**
	 * Used to put tool in default tab and custom modpack tab
	 */
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return CConstants.tabTools;
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