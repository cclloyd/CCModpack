package com.cclloyd.ccmodpack.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.cclloyd.ccmodpack.itemtool.ItemToolHammer;
import com.cclloyd.ccmodpack.itemtool.ItemToolHammerDiamond;
import com.cclloyd.ccmodpack.itemtool.ItemToolHammerGold;
import com.cclloyd.ccmodpack.itemtool.ItemToolHammerIron;
import com.cclloyd.ccmodpack.itemtool.ItemToolHammerStone;
import com.cclloyd.ccmodpack.itemtool.ItemToolHammerWood;


public class ItemRegistry extends CommonRegistry {
	
	// Initialize Items
	public static ItemToolHammer itemToolHammerDiamond = new ItemToolHammerDiamond();
	public static ItemToolHammer itemToolHammerIron = new ItemToolHammerIron();
	public static ItemToolHammer itemToolHammerGold = new ItemToolHammerGold();
	public static ItemToolHammer itemToolHammerStone = new ItemToolHammerStone();
	public static ItemToolHammer itemToolHammerWood = new ItemToolHammerWood();
	
	
	public static void registerTools() {
		GameRegistry.registerItem(itemToolHammerDiamond, ItemToolHammerDiamond.name);
		GameRegistry.registerItem(itemToolHammerIron, ItemToolHammerIron.name);
		GameRegistry.registerItem(itemToolHammerGold, ItemToolHammerGold.name);
		GameRegistry.registerItem(itemToolHammerStone, ItemToolHammerStone.name);
		GameRegistry.registerItem(itemToolHammerWood, ItemToolHammerWood.name);
	}
	
	
	public static void registerRenders(ItemModelMesher renderItem) {
		// Register Item Renders
		addRender(renderItem, itemToolHammerDiamond, ItemToolHammerDiamond.name);
		addRender(renderItem, itemToolHammerIron, ItemToolHammerIron.name);
		addRender(renderItem, itemToolHammerGold, ItemToolHammerGold.name);
		addRender(renderItem, itemToolHammerStone, ItemToolHammerStone.name);
		addRender(renderItem, itemToolHammerWood, ItemToolHammerWood.name);
	      
	}
	
	public static Item getItem(Block block) {
		return Item.getItemFromBlock(block);
	}
		
	
	
}
