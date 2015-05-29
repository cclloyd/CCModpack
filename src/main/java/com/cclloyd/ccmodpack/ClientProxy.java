package com.cclloyd.ccmodpack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.cclloyd.ccmodpack.registry.BlockRegistry;
import com.cclloyd.ccmodpack.registry.ItemRegistry;

public class ClientProxy extends CommonProxy {

	public static RenderItem renderItem;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		
		
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		renderItem = Minecraft.getMinecraft().getRenderItem();
		BlockRegistry.registerRenders();
		ItemRegistry.registerRenders();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		
		
	}
}
