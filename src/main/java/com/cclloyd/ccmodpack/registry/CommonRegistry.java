package com.cclloyd.ccmodpack.registry;

import com.cclloyd.ccmodpack.CCModpack;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class CommonRegistry {

	protected static void addRender(ItemModelMesher renderItem, Block renderBlock, String blockname) {
		addRender(renderItem, Item.getItemFromBlock(renderBlock), blockname);
	}
	
	protected static void addRender(ItemModelMesher renderItem, Item item, String itemName) {
		renderItem.register(item, 0, new ModelResourceLocation(CCModpack.MODID + ":" + itemName, "inventory"));
	}


	public static void config(Configuration config) {
		//loadEntities = config.get("registry entities", "entities", true).getBoolean(true);
	}
}
