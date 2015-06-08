package com.cclloyd.ccmodpack.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.registry.CConstants;

public class ItemBlackDiamond extends Item {
	
	public static final String name = "itemBlackDiamond";
	
	public ItemBlackDiamond() {
		super();
		setUnlocalizedName(CCModpack.MODID + "_" + name);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return CConstants.tabMisc;
	}
}
