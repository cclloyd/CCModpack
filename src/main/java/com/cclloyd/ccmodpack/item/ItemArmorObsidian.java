package com.cclloyd.ccmodpack.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.registry.CConstants;
import com.cclloyd.ccmodpack.registry.ItemRegistry;

public class ItemArmorObsidian extends ItemArmor {// implements IRecipe {
	
	public static final String name = "itemArmorObsidian";
	public static final String name_helmet = "itemArmorObsidianHelmet";
	public static final String  name_chest = "itemArmorObsidianChest";
	public static final String   name_legs = "itemArmorObsidianLegs";
	public static final String  name_boots = "itemArmorObsidianBoots";
	public static final String name_reinforced = "itemArmorObsidianReinforced";
	public static final String name_helmet_reinforced = "itemArmorObsidianReinforcedHelmet";
	public static final String  name_chest_reinforced = "itemArmorObsidianReinforcedChest";
	public static final String   name_legs_reinforced = "itemArmorObsidianReinforcedLegs";
	public static final String  name_boots_reinforced = "itemArmorObsidianReinforcedBoots";
	
	public static final int ARMOR_HELMET = 0;
	public static final int ARMOR_CHEST = 1;
	public static final int ARMOR_LEGS = 2;
	public static final int ARMOR_BOOTS = 3;
	
	
	int type = 0;
	boolean reinforced;
	List<ItemBook> enchantments;
	ItemPotion potion;
	PotionEffect potionEffect;
	
	/**
	 * Normal Obsidian Armor
	 * @param type The type of armor
	 */
	public ItemArmorObsidian(int type) { 
		super(ItemRegistry.armorObsidian, 0, type);
		this.reinforced = false;
		this.enchantments = new ArrayList<ItemBook>();
		this.type = type;
		setName(type, reinforced);
		this.potion = null;
	}
	
	/**
	 * Constructor for reinforced armor
	 * @param type
	 * @param reinforced
	 */
	public ItemArmorObsidian(int type, boolean reinforced) { 
		super(ItemRegistry.armorObsidianReinforced, 0, type);
		this.reinforced = reinforced;
		this.type = type;
		setName(type, reinforced);
	}
	
	/**
	 * Constructor for armor with potion effect
	 * @param type The kind of armor
	 * @param reinforced Reinforced or not
	 * @param potion The potion effect that goes with it.
	 */
	public ItemArmorObsidian(int type, boolean reinforced, ItemPotion potion) { 
		super(ItemRegistry.armorObsidianReinforced, 0, type);
		this.type = type;
		this.reinforced = reinforced;
		this.potion = potion;
		setName(type, reinforced);
	}
	

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return CConstants.tabCombat;
	}
			
	
	/**
	 * Sets unlocalized name of armor based on type
	 * @param type
	 * @param reinforced
	 */
	public void setName(int type, boolean reinforced) {
		
		if (reinforced) {
			switch (type) {
				case ItemRegistry.ARMOR_HELMET:
					setUnlocalizedName(CCModpack.MODID + "_" + name_helmet_reinforced); break;
				case ItemRegistry.ARMOR_CHEST:
					setUnlocalizedName(CCModpack.MODID + "_" + name_chest_reinforced); break;
				case ItemRegistry.ARMOR_LEGS:
					setUnlocalizedName(CCModpack.MODID + "_" + name_legs_reinforced); break;
				case ItemRegistry.ARMOR_BOOTS:
					setUnlocalizedName(CCModpack.MODID + "_" + name_boots_reinforced); break;
				default:
					setUnlocalizedName(CCModpack.MODID + "_" + name_reinforced); break;
			}
		}
		else {
			switch (type) {
				case ItemRegistry.ARMOR_HELMET:
					setUnlocalizedName(CCModpack.MODID + "_" +  name_helmet); break;
				case ItemRegistry.ARMOR_CHEST:
					setUnlocalizedName(CCModpack.MODID + "_" +  name_chest); break;
				case ItemRegistry.ARMOR_LEGS:
					setUnlocalizedName(CCModpack.MODID + "_" +  name_legs); break;
				case ItemRegistry.ARMOR_BOOTS:
					setUnlocalizedName(CCModpack.MODID + "_" +  name_boots); break;
				default:
					setUnlocalizedName(CCModpack.MODID + "_" + name);
				
			}
		}
	}
	
	/**
	 * Get name based on armor type
	 * @return Unlocalized name of the armor
	 */
	public String name() {
		if (this.reinforced) {
			switch (this.type) {
				case ItemRegistry.ARMOR_HELMET:
					return name_helmet_reinforced;
				case ItemRegistry.ARMOR_CHEST:
					return name_chest_reinforced;
				case ItemRegistry.ARMOR_LEGS:
					return name_legs_reinforced;
				case ItemRegistry.ARMOR_BOOTS:
					return name_boots_reinforced;
				default:
					return name_reinforced;
			}
		}
		else {
			switch (this.type) {
			case ItemRegistry.ARMOR_HELMET:
				return name_helmet;
			case ItemRegistry.ARMOR_CHEST:
				return name_chest;
			case ItemRegistry.ARMOR_LEGS:
				return name_legs;
			case ItemRegistry.ARMOR_BOOTS:
				return name_boots;
			default:
				return name;
		}
		}
	}
	
	/**
	 * Is the armor reinforced
	 * @return reinforced
	 */
	public boolean isReinforced() {
		return reinforced;
	}
	
	@Override
	public String getArmorTexture(ItemStack armor, Entity entity, int slot, String type) {

		if (this.isReinforced()) {
			System.out.println("is reinforced");
			if (armor.getItem() == ItemRegistry.armorObsidianReinforcedLegs) {
		        return CCModpack.MODID + ":textures/models/armor/obsidian_armor_reinforced_layer_2.png";
			}
				
	        return CCModpack.MODID + ":textures/models/armor/obsidian_armor_reinforced_layer_1.png";
		}
		else {
			if (armor.getItem() == ItemRegistry.armorObsidianLegs)
		        return CCModpack.MODID + ":textures/models/armor/obsidian_armor_layer_2.png";
				
	        return CCModpack.MODID + ":textures/models/armor/obsidian_armor_layer_1.png";
		}
	}
	
	private static final int EFFECTIVENESS = 1;
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		/*
		ItemStack stack = new ItemStack(this.potion, 1);
		List<PotionEffect> effects = this.potion.getEffects(stack);
		
		if (player.getCurrentArmor(ItemRegistry.ARMOR_LEGS) != null ) {
			if (this.potion != null)
				if (player.getCurrentArmor(ItemRegistry.ARMOR_LEGS).getItem().equals(ItemRegistry.armorObsidianLegs))
					player.addPotionEffect(effects.get(0));
		}
		*/
	}
	
	public int getType() {
		return type;
	}
	
	public boolean getReinforced() {
		return reinforced;
	}
/*
	@Override
	public boolean matches(InventoryCrafting invCrafting, World worldIn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
		// TODO Auto-generated method stub
		int size = invCrafting.getSizeInventory();
		ItemStack stack;
		int potionId;
		for (int i=0; i<size; i++) {
			if ((stack = invCrafting.getStackInSlot(i)) != null)
				if (stack.getItem() == Items.potionitem);
					//potionId = stack.getItem().get;
		}
		return null;
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting p_179532_1_) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	
}