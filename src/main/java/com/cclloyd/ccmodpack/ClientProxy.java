package com.cclloyd.ccmodpack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy {

	public static void preInit(FMLPreInitializationEvent event) {
		
	}
	
	
	public static void init(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {	
			ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
			BlockRegistry.registerRenders(renderItem);
		}
	}
	
	
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
}
