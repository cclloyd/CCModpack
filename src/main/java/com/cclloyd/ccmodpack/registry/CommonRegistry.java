package com.cclloyd.ccmodpack.registry;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

import com.cclloyd.ccmodpack.CCModpack;
import com.cclloyd.ccmodpack.ClientProxy;

public class CommonRegistry {

	protected static void addRender(Block renderBlock, String blockname) {
		addRender(Item.getItemFromBlock(renderBlock), blockname);
	}
	
	protected static void addRender(Item item, String itemName) {
		ClientProxy.renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(CCModpack.MODID + ":" + itemName, "inventory"));
	}


	public static void config(Configuration config) {
		//loadEntities = config.get("registry entities", "entities", true).getBoolean(true);
	}
}
