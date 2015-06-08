package com.cclloyd.ccmodpack.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ForgeRecipes {

	
	public static List<ForgeRecipe> recipeList = new ArrayList<ForgeRecipe>();
	@SuppressWarnings("unused")
	private static int COOKTIME = 200;
	private static int CUSTOM_COOKTIME = 300;
	public static int recipeCount = 0;
	
	
	/**
	 * Registers default recipes that are part of the modpack.  You can add more using registerRecipe().
	 */
	public static void registerRecipes() {
		registerRecipe("blackDiamond", ItemRegistry.blackDiamond, 1, CUSTOM_COOKTIME, Items.diamond, asItem(Blocks.obsidian));
		registerRecipe("temperedObsidian", BlockRegistry.temperedObsidian, 1, CUSTOM_COOKTIME, asItem(Blocks.obsidian));
		registerRecipe("cleanGlass", BlockRegistry.cleanGlass, 1, CUSTOM_COOKTIME, asItem(Blocks.sand));
		registerRecipe("temperedGlass", BlockRegistry.cleanGlassTempered, 1, CUSTOM_COOKTIME, asItem(BlockRegistry.cleanGlass));
	}
	
	
    /**
     * Registers a new recipe for use with the refined furnace
     * @param name The name of the recipe
     * @param output The item/block it's supposed to output
     * @param quantity The quantity of the output item/block
     * @param cookTime The number of ticks needed to cook the recipe ( Default is 200 [10s] )
     * @param args 0..4 The input items of the recipe
     */
    public static void registerRecipe(String name, Item output, int quantity, int cookTime, Item... args) {
    	if ((args.length > 4) || (args.length == 0))
    		System.err.println("Error:  Too many arguments in method registerRecipes(). Min is 1. Max is 4.");
    	
    	for (int i=0; i<recipeCount; i++) {
    		if (name == getRecipe(i).name()) {
    			System.err.println("Error:  Recipe already registered with name " + name + ".");
    			return;
    		}
    	}
    	recipeList.add(new ForgeRecipe(name, output, quantity, cookTime, args));
    	recipeCount++;
    }
    
    public static void registerRecipe(String name, Block output, int quantity, int cookTime, Item... args) {
    	registerRecipe(name, asItem(output), quantity, cookTime, args);
    }
    
    /*
    public static void registerRecipe(String name, Block output, int quantity, int cookTime, Object... args) {
    	Item[] inputs = new Item[4];
    	int i = 0;
    	for (Object arg : args) {
    		if (arg.getClass() == Block.class)
    			inputs[i] = asItem((Block)arg);
    		if (arg.getClass() == Item.class);
    			inputs[i] = (Item)arg;
    		i++;
    	}
    	registerRecipe(name, asItem(output), quantity, cookTime, inputs);
    }*/
    
    /**
     * Gets the recipe at an index number.  Used for iterating through the recipe list. 
     * @param index Recipe number
     * @return The recipe at the index
     */
    public static ForgeRecipe getRecipe(int index) {
    	return recipeList.get(index);
    }
    
    /**
     * Gets the recipe from provided name
     * @param name The name of the recipe
     * @return The associated furnace recipe
     */
    public static ForgeRecipe getRecipe(String name) {
    	for (int i=0; i<recipeCount; i++) {
    		String recipeName = getRecipe(i).name();
    		if (recipeName == name)
    			return getRecipe(i);
    	}
    	return null;
    }
    
    
    public boolean equals(ForgeRecipe recipe) {
    	for (int i=0; i<recipeCount; i++) {
    		if (recipe.name() == getRecipe(i).name())
    			return true;
    	}
    	return false;
    }
    
    
    
	private static Item asItem(Block block) {
		return Item.getItemFromBlock(block);
	}
}
