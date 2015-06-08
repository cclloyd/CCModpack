package com.cclloyd.ccmodpack.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.registry.CConstants;
import com.cclloyd.ccmodpack.registry.ItemRegistry;

public class ItemToolSwordObsidianReinforced extends ItemSword {
	
	public static final String name = "itemToolSwordObsidianReinforced";	
	  
	public ItemToolSwordObsidianReinforced() { 
		super(ItemRegistry.toolObsidianReinforced);
		setUnlocalizedName(CCModpack.MODID + "_" +  name);
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
	public CreativeTabs[] getCreativeTabs() {
		return CConstants.tabCombat;
	}

  	@Override
  	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
  		Boolean result = true;
  		result = super.onBlockStartBreak(itemstack, pos, player);
  		return result;
  	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
		
		return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
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