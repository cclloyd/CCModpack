package com.cclloyd.ccmodpack.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
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
	public static ToolMaterial toolObsidian = EnumHelper.addToolMaterial("Reinforced Obsidian", 2, 638, 7.0F, 2.0F, 15).setRepairItem(new ItemStack(ItemRegistry.blackDiamond, 1));
	public static ToolMaterial toolObsidianReinforced = EnumHelper.addToolMaterial("Obsidian", 3, 2401, 12.0F, 4.0F, 22).setRepairItem(new ItemStack(BlockRegistry.temperedObsidian, 1));
	
	// Armor Materials
	public static ArmorMaterial armorObsidian = EnumHelper.addArmorMaterial("Obsidian", "obsidian_armor", 18, new int[]{3,6,5,2}, 16);
	public static ArmorMaterial armorObsidianReinforced = EnumHelper.addArmorMaterial("Obsidian", "obsidian_armor_reinforced", 40, new int[]{4, 9, 7, 4}, 25);
		
	// Initialize Items
	public static ItemBlackDiamond blackDiamond = new ItemBlackDiamond();
	
	public static ItemToolHammer toolHammerDiamond = new ItemToolHammer(5.0F, ToolMaterial.EMERALD);
	public static ItemToolHammer toolHammerGold = new ItemToolHammer(3.0F, ToolMaterial.GOLD);
	public static ItemToolHammer toolHammerIron = new ItemToolHammer(4.0F, ToolMaterial.IRON);
	public static ItemToolHammer toolHammerStone = new ItemToolHammer(2.0F, ToolMaterial.STONE);
	public static ItemToolHammer toolHammerWood = new ItemToolHammer(1.0F, ToolMaterial.WOOD);
	public static ItemToolHammer toolHammerObsidian = new ItemToolHammer(4.25F, toolObsidian);
	public static ItemToolHammer toolHammerObsidianReinforced = new ItemToolHammer(6.0F, toolObsidianReinforced);
	
	public static ItemToolPickaxeObsidian toolPickaxeObsidian = new ItemToolPickaxeObsidian();
	public static ItemToolAxeObsidian toolAxeObsidian = new ItemToolAxeObsidian();
	public static ItemToolSpadeObsidian toolSpadeObsidian = new ItemToolSpadeObsidian();
	public static ItemToolHoeObsidian toolHoeObsidian = new ItemToolHoeObsidian();
	public static ItemToolSwordObsidian toolSwordObsidian = new ItemToolSwordObsidian();
	public static ItemToolPickaxeObsidianReinforced toolPickaxeObsidianReinforced = new ItemToolPickaxeObsidianReinforced();
	public static ItemToolAxeObsidianReinforced toolAxeObsidianReinforced = new ItemToolAxeObsidianReinforced();
	public static ItemToolSpadeObsidianReinforced toolSpadeObsidianReinforced = new ItemToolSpadeObsidianReinforced();
	public static ItemToolHoeObsidianReinforced toolHoeObsidianReinforced = new ItemToolHoeObsidianReinforced();
	public static ItemToolSwordObsidianReinforced toolSwordObsidianReinforced = new ItemToolSwordObsidianReinforced();
	
	// Initialize Armor
	public static final int ARMOR_HELMET = 0;
	public static final int ARMOR_CHEST = 1;
	public static final int ARMOR_LEGS = 2;
	public static final int ARMOR_BOOTS = 3;
	public static ItemArmorObsidian armorObsidianHelmet = new ItemArmorObsidian(ARMOR_HELMET);
	public static ItemArmorObsidian armorObsidianChest = new ItemArmorObsidian(ARMOR_CHEST);
	public static ItemArmorObsidian armorObsidianLegs = new ItemArmorObsidian(ARMOR_LEGS);
	public static ItemArmorObsidian armorObsidianBoots = new ItemArmorObsidian(ARMOR_BOOTS);
	public static ItemArmorObsidian armorObsidianReinforcedHelmet = new ItemArmorObsidian(ARMOR_HELMET, true);
	public static ItemArmorObsidian armorObsidianReinforcedChest = new ItemArmorObsidian(ARMOR_CHEST, true);
	public static ItemArmorObsidian armorObsidianReinforcedLegs = new ItemArmorObsidian(ARMOR_LEGS, true);
	public static ItemArmorObsidian armorObsidianReinforcedBoots = new ItemArmorObsidian(ARMOR_BOOTS, true);
	
	public static void registerTools() {
		GameRegistry.registerItem(toolHammerDiamond, toolHammerDiamond.getName());
		GameRegistry.registerItem(toolHammerIron, toolHammerIron.getName());
		GameRegistry.registerItem(toolHammerGold, toolHammerGold.getName());
		GameRegistry.registerItem(toolHammerStone, toolHammerStone.getName());
		GameRegistry.registerItem(toolHammerWood, toolHammerWood.getName());
		GameRegistry.registerItem(toolHammerObsidian, toolHammerObsidian.getName());
		GameRegistry.registerItem(toolHammerObsidianReinforced, toolHammerObsidianReinforced.getName());

		GameRegistry.registerItem(toolPickaxeObsidian, ItemToolPickaxeObsidian.name);
		GameRegistry.registerItem(toolPickaxeObsidianReinforced, ItemToolPickaxeObsidianReinforced.name);
		GameRegistry.registerItem(toolAxeObsidian, ItemToolAxeObsidian.name);
		GameRegistry.registerItem(toolAxeObsidianReinforced, ItemToolAxeObsidianReinforced.name);
		GameRegistry.registerItem(toolSpadeObsidian, ItemToolSpadeObsidian.name);
		GameRegistry.registerItem(toolSpadeObsidianReinforced, ItemToolSpadeObsidianReinforced.name);
		GameRegistry.registerItem(toolHoeObsidian, ItemToolHoeObsidian.name);
		GameRegistry.registerItem(toolHoeObsidianReinforced, ItemToolHoeObsidianReinforced.name);
		GameRegistry.registerItem(toolSwordObsidian, ItemToolSwordObsidian.name);
		GameRegistry.registerItem(toolSwordObsidianReinforced, ItemToolSwordObsidianReinforced.name);
		
		GameRegistry.registerItem(armorObsidianHelmet, armorObsidianHelmet.name());
		GameRegistry.registerItem(armorObsidianChest, armorObsidianChest.name());
		GameRegistry.registerItem(armorObsidianLegs, armorObsidianLegs.name());
		GameRegistry.registerItem(armorObsidianBoots, armorObsidianBoots.name());
		GameRegistry.registerItem(armorObsidianReinforcedHelmet, armorObsidianReinforcedHelmet.name());
		GameRegistry.registerItem(armorObsidianReinforcedChest, armorObsidianReinforcedChest.name());
		GameRegistry.registerItem(armorObsidianReinforcedLegs, armorObsidianReinforcedLegs.name());
		GameRegistry.registerItem(armorObsidianReinforcedBoots, armorObsidianReinforcedBoots.name());
		
		GameRegistry.registerItem(blackDiamond, ItemBlackDiamond.name);
		
	}
	
	
	public static void registerRenders() {
		// Register Item Renders
		addRender(toolHammerDiamond, toolHammerDiamond.getName());
		addRender(toolHammerIron, toolHammerIron.getName());
		addRender(toolHammerGold, toolHammerGold.getName());
		addRender(toolHammerStone, toolHammerStone.getName());
		addRender(toolHammerWood, toolHammerWood.getName());
		addRender(toolHammerObsidian, toolHammerObsidian.getName());
		addRender(toolHammerObsidianReinforced, toolHammerObsidianReinforced.getName());

		addRender(toolPickaxeObsidian, ItemToolPickaxeObsidian.name);
		addRender(toolPickaxeObsidianReinforced, ItemToolPickaxeObsidianReinforced.name);
		addRender(toolAxeObsidian, ItemToolAxeObsidian.name);
		addRender(toolAxeObsidianReinforced, ItemToolAxeObsidianReinforced.name);
		addRender(toolSpadeObsidian, ItemToolSpadeObsidian.name);
		addRender(toolSpadeObsidianReinforced, ItemToolSpadeObsidianReinforced.name);
		addRender(toolHoeObsidian, ItemToolHoeObsidian.name);
		addRender(toolHoeObsidianReinforced, ItemToolHoeObsidianReinforced.name);
		addRender(toolSwordObsidian, ItemToolSwordObsidian.name);
		addRender(toolSwordObsidianReinforced, ItemToolSwordObsidianReinforced.name);

		addRender(blackDiamond, ItemBlackDiamond.name);

		addRender(armorObsidianHelmet, armorObsidianHelmet.name());
		addRender(armorObsidianChest, armorObsidianChest.name());
		addRender(armorObsidianLegs, armorObsidianLegs.name());
		addRender(armorObsidianBoots, armorObsidianBoots.name());
		addRender(armorObsidianReinforcedHelmet, armorObsidianReinforcedHelmet.name());
		addRender(armorObsidianReinforcedChest, armorObsidianReinforcedChest.name());
		addRender(armorObsidianReinforcedLegs, armorObsidianReinforcedLegs.name());
		addRender(armorObsidianReinforcedBoots, armorObsidianReinforcedBoots.name());
	}
	
	public static Item getItem(Block block) {
		return Item.getItemFromBlock(block);
	}
		
	
	
}
