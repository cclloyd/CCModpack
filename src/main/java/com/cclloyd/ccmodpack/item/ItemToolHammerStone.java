package com.cclloyd.ccmodpack.item;

import com.cclloyd.ccmodpack.CCModpack;

public class ItemToolHammerStone extends ItemToolHammer {

	public static final String name = "itemToolHammerStone";

	public static float attackDamage = 2.0F;
	public static ToolMaterial material = ToolMaterial.STONE;
	
	public ItemToolHammerStone() {
		super(attackDamage, material);
		setUnlocalizedName(CCModpack.MODID + "_" + name);
	}

}
