package com.cclloyd.ccmodpack.item;

import com.cclloyd.ccmodpack.CCModpack;

public class ItemToolHammerWood extends ItemToolHammer {

	public static final String name = "itemToolHammerWood";

	public static float attackDamage = 1.0F;
	public static ToolMaterial material = ToolMaterial.WOOD;
	
	public ItemToolHammerWood() {
		super(attackDamage, material);
		setUnlocalizedName(CCModpack.MODID + "_" + name);
	}

}
