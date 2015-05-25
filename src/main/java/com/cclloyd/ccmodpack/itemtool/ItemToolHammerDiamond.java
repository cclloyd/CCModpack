package com.cclloyd.ccmodpack.itemtool;

import com.cclloyd.ccmodpack.CCModpack;

public class ItemToolHammerDiamond extends ItemToolHammer {

	public static final String name = "itemToolHammerDiamond";

	public static float attackDamage = 5.0F;
	public static ToolMaterial material = ToolMaterial.EMERALD;
	
	public ItemToolHammerDiamond() {
		super(attackDamage, material);
		setUnlocalizedName(CCModpack.MODID + "_" + name);
	}

}
