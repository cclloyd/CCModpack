package com.cclloyd.ccmodpack;

import com.cclloyd.ccmodpack.registry.BlockRegistry;
import com.cclloyd.ccmodpack.registry.ItemRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		
		
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		BlockRegistry.registerRenders(renderItem);
		ItemRegistry.registerRenders(renderItem);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		
		
	}
}
