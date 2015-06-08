package com.cclloyd.ccmodpack.registry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.cclloyd.ccmodpack.item.ItemArmorObsidian;

public class Recipes {

	public static void addRecipes() {
		
		// Initialize Blocks
	 	Block charcoalBlock = BlockRegistry.blockCharcoal;
	    Block blockPoweredRedstoneLantern = BlockRegistry.blockPoweredRedstoneLantern;
    	Block refinedFurnace = BlockRegistry.refinedFurnace;
    	
    	// Initialize ItemStacks
        ItemStack iceStack = new ItemStack(Blocks.ice);
        ItemStack packedIceStack = new ItemStack(Blocks.packed_ice);
        ItemStack charcoalStack = new ItemStack(Items.coal, 1, 1);
        ItemStack charcoalStack9 = new ItemStack(Items.coal, 9, 1);
        ItemStack redstoneLanternStack = new ItemStack(Blocks.redstone_lamp, 1, 0);
        ItemStack redstoneTorchStack = new ItemStack(Blocks.redstone_torch, 1);
        ItemStack blockPoweredRedstoneLanternStack = new ItemStack(blockPoweredRedstoneLantern, 1);
        ItemStack charcoalBlockStack = new ItemStack(charcoalBlock, 1);
        ItemStack furnaceStack = new ItemStack(Blocks.furnace, 1);
        ItemStack ironIngotStack = new ItemStack(Items.iron_ingot, 1);
        ItemStack refinedFurnaceStack = new ItemStack(refinedFurnace, 1);
        
        // Register Shapeless Recipes
        GameRegistry.addShapelessRecipe(packedIceStack, iceStack, iceStack, iceStack, iceStack);
        GameRegistry.addShapelessRecipe(charcoalBlockStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack, charcoalStack);
        GameRegistry.addShapelessRecipe(blockPoweredRedstoneLanternStack, redstoneTorchStack, redstoneLanternStack);
        GameRegistry.addShapelessRecipe(charcoalStack9, charcoalBlockStack);
        
        //ItemPotion armorPotion = Items.potionitem;
        //ItemStack armorPotionStack = new ItemStack(Items.potionitem, 1);
        
        //ItemStack obsidianArmorHelmet = new ItemStack(new ItemArmorObsidian(ItemRegistry.ARMOR_HELMET, false, armorPotion), 1);
        
        // GameRegistry.addShapelessRecipe(new ItemStack(new ItemArmorObsidian(0, false, armorPotion), 1), armorPotion);

        //List effects = ItemPotion.getEffects();
        /*
        for (int i=0; i<Potion.potionTypes.length; i++) {
        	Potion potion = Potion.potionTypes[i];
        	PotionEffect effect = new PotionEffect(potion.id, 1, 1, false, false);
        	ItemPotion iPotion = new ItemPotion();
        	//iPotion.setPotionEffect(potion.getName());
        	ItemStack potionStack = new ItemStack(iPotion, 1);
        	iPotion.getPotionEffect(potionStack);
        	ItemArmorObsidian armor = ItemRegistry.armorObsidianBoots;
        	ItemArmorObsidian armorPotion = new ItemArmorObsidian(armor.getType(), armor.getReinforced(), iPotion);
        	GameRegistry.addShapelessRecipe(new ItemStack(armorPotion, 1), new ItemStack(armor, 1), potionStack);
        }*/
        //IRecipe recipe = new IRecipe();
        //GameRegistry.addRecipe(ItemRegistry.armorObsidianBoots);
        
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
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHammerDiamond, 1),
        		" D ",
        		" SD",
        		"S  ",
        		'D', new ItemStack(Items.diamond, 1), 'S', new ItemStack(Items.stick, 1)
        );
        
        // Iron Hammer
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHammerIron, 1),
        		" D ",
        		" SD",
        		"S  ",
        		'D', new ItemStack(Items.iron_ingot, 1), 'S', new ItemStack(Items.stick, 1)
        );
        
        // Gold Hammer
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHammerGold, 1),
        		" D ",
        		" SD",
        		"S  ",
        		'D', new ItemStack(Items.gold_ingot, 1), 'S', new ItemStack(Items.stick, 1)
        );
        
        // Stone Hammer
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHammerStone, 1),
        		" D ",
        		" SD",
        		"S  ",
        		'D', new ItemStack(Blocks.stone, 1, 0), 'S', new ItemStack(Items.stick, 1)
        );
        
        // Wood Hammer
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHammerWood, 1),
        		" D ",
        		" SD",
        		"S  ",
        		'D', new ItemStack(Blocks.planks, 1), 'S', new ItemStack(Items.stick, 1)
        );
        
        // Obsidian Hammer
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHammerObsidian, 1),
        		" D ",
        		" SD",
        		"S  ",
        		'D', new ItemStack(BlockRegistry.temperedObsidian, 1), 'S', new ItemStack(Items.stick, 1)
        );
        
        // Reinforced Obsidian Hammer
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHammerObsidianReinforced, 1),
        		" D ",
        		" SD",
        		"S  ",
        		'D', new ItemStack(ItemRegistry.blackDiamond, 1), 'S', new ItemStack(Items.stick, 1)
        );
        
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsGranite, 4),
        		"  G",
        		" GG",
        		"GGG",
        		'G', new ItemStack(Blocks.stone, 1, 2)
        );
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsAndesite, 4),
        		"  A",
        		" AA",
        		"AAA",
        		'A', new ItemStack(Blocks.stone, 1, 6)
        );
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockStairsDiorite, 4),
        		"  D",
        		" DD",
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
        
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolPickaxeObsidian, 1),
        	"OOO",
        	" S ",
        	" S ",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolPickaxeObsidianReinforced, 1),
        	"BBB",
        	" S ",
        	" S ",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolAxeObsidian, 1),
        	" OO",
        	" SO",
        	" S ",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolAxeObsidianReinforced, 1),
        	" BB",
        	" SB",
        	" S ",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHoeObsidian, 1),
        	" OO",
        	" S ",
        	" S ",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolHoeObsidianReinforced, 1),
        	" BB",
        	" S ",
        	" S ",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolSpadeObsidian, 1),
        	" O ",
        	" S ",
        	" S ",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolSpadeObsidianReinforced, 1),
        	" B ",
        	" S ",
        	" S ",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolSwordObsidian, 1),
        	" O ",
        	" O ",
        	" S ",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.toolSwordObsidianReinforced, 1),
        	" B ",
        	" B ",
        	" S ",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1),
        	'S', new ItemStack(Items.stick, 1)
        );
        
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.armorObsidianHelmet, 1),
        	"   ",
        	"OOO",
        	"O O",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.armorObsidianReinforcedHelmet, 1),
        	"   ",
        	"BBB",
        	"B B",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1)
        );
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.armorObsidianChest, 1),
        	"O O",
        	"OOO",
        	"OOO",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.armorObsidianReinforcedChest, 1),
        	"B B",
        	"BBB",
        	"BBB",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1)
        );
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.armorObsidianLegs, 1),
        	"OOO",
        	"O O",
        	"O O",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.armorObsidianReinforcedLegs, 1),
        	"BBB",
        	"B B",
        	"B B",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1)
        );
        
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.armorObsidianBoots, 1),
        	"   ",
        	"O O",
        	"O O",
        	'O', new ItemStack(BlockRegistry.temperedObsidian, 1)
        );
        GameRegistry.addRecipe(new ItemStack(ItemRegistry.armorObsidianReinforcedBoots, 1),
        	"   ",
        	"B B",
        	"B B",
        	'B', new ItemStack(ItemRegistry.blackDiamond, 1)
        );
        
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.cleanGlassPane, 16),
        	"   ",
        	"CCC",
        	"CCC",
        	'C', new ItemStack(BlockRegistry.cleanGlass, 1)
        );
        
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.cleanGlassPaneTempered, 16),
        	"   ",
        	"CCC",
        	"CCC",
        	'C', new ItemStack(BlockRegistry.cleanGlassTempered, 1)
        );
	}
	
	public static void addSmelting() {
		// Register Smelting
        GameRegistry.registerFuelHandler((IFuelHandler)BlockRegistry.blockCharcoal);
	}
	
	public static void addFurnaceRecipes() {
		ForgeRecipes.registerRecipes();
	}
}
