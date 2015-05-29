package com.cclloyd.ccmodpack.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.cclloyd.ccmodpack.item.*;

/*
 * 
 * Add Obsidian Armor
 * Fix reinforced obsidian armor material
 * Make the rest of the armors
 * Fix textures with armors
 * Figure out if you can overclock armor over diamond
 * 
 * Try to replace vanilla block like glass for different model
 * 
 * Smelt Obsidian into Tempered Obsidian with refined furnace when fuel is lava
 * 
 * 
 * 
 * 
 */

public class ItemRegistry extends CommonRegistry {
	
	// Tool Materials
	public static ToolMaterial obsidian = EnumHelper.addToolMaterial("Reinforced Obsidian", 2, 638, 7.0F, 2.0F, 15);
	public static ToolMaterial obsidian_reinforced = EnumHelper.addToolMaterial("Obsidian", 3, 2401, 12.0F, 4.0F, 22);

	// Armor Materials
	public static ArmorMaterial armorObsidian = EnumHelper.addArmorMaterial("Obsidian", "obsidian_armor", 18, new int[]{3,6,5,2}, 10);
	//public static ArmorMaterial armorObsidian = EnumHelper.addArmorMaterial("Obsidian", "obsidian_armor", 18, new int[]{3,6,5,2}, 10);
	public static ArmorMaterial armorObsidianReinforced = EnumHelper.addArmorMaterial("Obsidian", "obsidian_armor", 18, new int[]{3,6,5,2}, 10);
		
	// Initialize Items
	public static ItemToolHammer itemToolHammerDiamond = new ItemToolHammerDiamond();
	public static ItemToolHammer itemToolHammerIron = new ItemToolHammerIron();
	public static ItemToolHammer itemToolHammerGold = new ItemToolHammerGold();
	public static ItemToolHammer itemToolHammerStone = new ItemToolHammerStone();
	public static ItemToolHammer itemToolHammerWood = new ItemToolHammerWood();
	public static ItemToolHammer itemToolHammerObsidian = new ItemToolHammerObsidian();
	public static ItemToolHammer itemToolHammerObsidianReinforced = new ItemToolHammerObsidianReinforced();
	public static ItemToolPickaxeObsidian itemToolPickaxeObsidian = new ItemToolPickaxeObsidian();
	public static ItemToolPickaxeObsidianReinforced itemToolPickaxeObsidianReinforced = new ItemToolPickaxeObsidianReinforced();
	public static ItemToolAxeObsidian itemToolAxeObsidian = new ItemToolAxeObsidian();
	public static ItemToolAxeObsidianReinforced itemToolAxeObsidianReinforced = new ItemToolAxeObsidianReinforced();
	public static ItemToolSpadeObsidian itemToolSpadeObsidian = new ItemToolSpadeObsidian();
	public static ItemToolSpadeObsidianReinforced itemToolSpadeObsidianReinforced = new ItemToolSpadeObsidianReinforced();
	public static ItemToolHoeObsidian itemToolHoeObsidian = new ItemToolHoeObsidian();
	public static ItemToolHoeObsidianReinforced itemToolHoeObsidianReinforced = new ItemToolHoeObsidianReinforced();
	public static ItemToolSwordObsidian itemToolSwordObsidian = new ItemToolSwordObsidian();
	public static ItemToolSwordObsidianReinforced itemToolSwordObsidianReinforced = new ItemToolSwordObsidianReinforced();
	
	// Initialize Armor
	public static ItemArmorChestObsidian itemArmorChestObsidian = new ItemArmorChestObsidian();
	
	public static void registerTools() {
		GameRegistry.registerItem(itemToolHammerDiamond, ItemToolHammerDiamond.name);
		GameRegistry.registerItem(itemToolHammerIron, ItemToolHammerIron.name);
		GameRegistry.registerItem(itemToolHammerGold, ItemToolHammerGold.name);
		GameRegistry.registerItem(itemToolHammerStone, ItemToolHammerStone.name);
		GameRegistry.registerItem(itemToolHammerWood, ItemToolHammerWood.name);

		GameRegistry.registerItem(itemToolPickaxeObsidian, ItemToolPickaxeObsidian.name);
		GameRegistry.registerItem(itemToolPickaxeObsidianReinforced, ItemToolPickaxeObsidianReinforced.name);
		GameRegistry.registerItem(itemToolAxeObsidian, ItemToolAxeObsidian.name);
		GameRegistry.registerItem(itemToolAxeObsidianReinforced, ItemToolAxeObsidianReinforced.name);
		GameRegistry.registerItem(itemToolSpadeObsidian, ItemToolSpadeObsidian.name);
		GameRegistry.registerItem(itemToolSpadeObsidianReinforced, ItemToolSpadeObsidianReinforced.name);
		GameRegistry.registerItem(itemToolHoeObsidian, ItemToolHoeObsidian.name);
		GameRegistry.registerItem(itemToolHoeObsidianReinforced, ItemToolHoeObsidianReinforced.name);
		GameRegistry.registerItem(itemToolSwordObsidian, ItemToolSwordObsidian.name);
		GameRegistry.registerItem(itemToolSwordObsidianReinforced, ItemToolSwordObsidianReinforced.name);
		GameRegistry.registerItem(itemToolHammerObsidian, ItemToolHammerObsidian.name);
		GameRegistry.registerItem(itemToolHammerObsidianReinforced, ItemToolHammerObsidianReinforced.name);
		
		GameRegistry.registerItem(itemArmorChestObsidian, ItemArmorChestObsidian.name);
		
	}
	
	
	public static void registerRenders() {
		// Register Item Renders
		addRender(itemToolHammerDiamond, ItemToolHammerDiamond.name);
		addRender(itemToolHammerIron, ItemToolHammerIron.name);
		addRender(itemToolHammerGold, ItemToolHammerGold.name);
		addRender(itemToolHammerStone, ItemToolHammerStone.name);
		addRender(itemToolHammerWood, ItemToolHammerWood.name);

		addRender(itemToolPickaxeObsidian, ItemToolPickaxeObsidian.name);
		addRender(itemToolPickaxeObsidianReinforced, ItemToolPickaxeObsidianReinforced.name);
		addRender(itemToolAxeObsidian, ItemToolAxeObsidian.name);
		addRender(itemToolAxeObsidianReinforced, ItemToolAxeObsidianReinforced.name);
		addRender(itemToolSpadeObsidian, ItemToolSpadeObsidian.name);
		addRender(itemToolSpadeObsidianReinforced, ItemToolSpadeObsidianReinforced.name);
		addRender(itemToolHoeObsidian, ItemToolHoeObsidian.name);
		addRender(itemToolHoeObsidianReinforced, ItemToolHoeObsidianReinforced.name);
		addRender(itemToolSwordObsidian, ItemToolSwordObsidian.name);
		addRender(itemToolSwordObsidianReinforced, ItemToolSwordObsidianReinforced.name);
		addRender(itemToolHammerObsidian, ItemToolHammerObsidian.name);
		addRender(itemToolHammerObsidianReinforced, ItemToolHammerObsidianReinforced.name);

	}
	
	public static Item getItem(Block block) {
		return Item.getItemFromBlock(block);
	}
		
	
	
}
