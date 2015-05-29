package com.cclloyd.ccmodpack.item;

import com.cclloyd.ccmodpack.CCModpack;

public class ItemToolHammerGold extends ItemToolHammer {

	public static final String name = "itemToolHammerGold";

	public static float attackDamage = 3.0F;
	public static ToolMaterial material = ToolMaterial.GOLD;
	
	public ItemToolHammerGold() {
		super(attackDamage, material);
		setUnlocalizedName(CCModpack.MODID + "_" + name);
	}

}
