package com.cclloyd.ccmodpack;

import com.cclloyd.ccmodpack.block.BlockCharcoal;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes {

	public static void addRecipes() {
		
		// Initialize Blocks
	 	Block charcoalBlock = BlockRegistry.charcoalBlock;
	    Block poweredRedstoneLantern = BlockRegistry.poweredRedstoneLantern;
    	Block refinedFurnace = BlockRegistry.refinedFurnace;
    	
    	// Initialize ItemStacks
        ItemStack iceStack = new ItemStack(Blocks.ice);
        ItemStack packedIceStack = new ItemStack(Blocks.packed_ice);
        ItemStack charcoalStack = new ItemStack(Items.coal, 1, 1);
        ItemStack charcoalStack9 = new ItemStack(Items.coal, 9, 1);
        ItemStack redstoneLanternStack = new ItemStack(Blocks.redstone_lamp, 1, 0);
        ItemStack redstoneTorchStack = new ItemStack(Blocks.redstone_torch, 1);
        ItemStack poweredRedstoneLanternStack = new ItemStack(poweredRedstoneLantern, 1);
        ItemStack charcoalBlockStack = new ItemStack(charcoalBlock, 1);
        ItemStack furnaceStack = new ItemStack(Blocks.furnace, 1);
        ItemStack ironIngotStack = new ItemStack(Items.iron_ingot, 1);
        ItemStack refinedFurnaceStack = new ItemStack(refinedFurnace, 1);
        
        // Register Shapeless Recipes
        GameRegistry.addShapelessRecipe(packedIceStack, iceStack, iceStack, iceStack, iceStack);
        GameRegistry.addShapelessRecipe(charcoalBlockStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack);
        GameRegistry.addShapelessRecipe(poweredRedstoneLanternStack, redstoneTorchStack, redstoneLanternStack);
        GameRegistry.addShapelessRecipe(charcoalStack9, charcoalBlockStack);
        
        
        /*******************************
         Register Shaped Recipes
         *******************************/
        // Refine Furnace
        GameRegistry.addRecipe(refinedFurnaceStack,
        		" I ",
        		"IFI",
        		" I ",
        		'I', ironIngotStack, 'F', furnaceStack
        );
        
        // Diamond Hammer
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.itemToolHammerDiamond, 1),
        		" D ",
        		" SD",
        		"S  ",
        		'D', new ItemStack(Items.diamond, 1), 'S', new ItemStack(Items.stick, 1)
        );
        
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsGranite, 4),
        		"  G",
        		" GG",
        		"GGG",
        		'G', new ItemStack(Blocks.stone, 1, 2)
        );
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsGranite, 4),
        		"G  ",
        		"GG ",
        		"GGG",
        		'G', new ItemStack(Blocks.stone, 1, 2)
        );
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsAndesite, 4),
        		"  A",
        		" AA",
        		"AAA",
        		'A', new ItemStack(Blocks.stone, 1, 6)
        );
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsAndesite, 4),
        		"A  ",
        		"AA ",
        		"AAA",
        		'A', new ItemStack(Blocks.stone, 1, 6)
        );
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsDiorite, 4),
        		"  D",
        		" DD",
        		"DDD",
        		'D', new ItemStack(Blocks.stone, 1, 4)
        );
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsDiorite, 4),
        		"D  ",
        		"DD ",
        		"DDD",
        		'D', new ItemStack(Blocks.stone, 1, 4)
        );
        
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockSlabGranite, 6),
        		"   ",
        		"   ",
        		"GGG",
        		'G', new ItemStack(Blocks.stone, 1, 2)
        );
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockSlabDiorite, 6),
        		"   ",
        		"   ",
        		"DDD",
        		'D', new ItemStack(Blocks.stone, 1, 4)
        );
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockSlabAndesite, 6),
        		"   ",
        		"   ",
        		"AAA",
        		'A', new ItemStack(Blocks.stone, 1, 6)
        );
	}
	
	public static void addSmelting() {
		
		// Initialize Blocks
	 	Block charcoalBlock = new BlockCharcoal();
	 	
		// Register Smelting
        GameRegistry.registerFuelHandler((IFuelHandler) charcoalBlock);
	}
}
