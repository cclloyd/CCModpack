package com.cclloyd.ccmodpack.itemtool;

import com.cclloyd.ccmodpack.CCModpack;

public class ItemToolHammerIron extends ItemToolHammer {

	public static final String name = "itemToolHammerIron";

	public static float attackDamage = 4.0F;
	public static ToolMaterial material = ToolMaterial.IRON;
	
	public ItemToolHammerIron() {
		super(attackDamage, material);
		setUnlocalizedName(CCModpack.MODID + "_" + name);
	}

}
