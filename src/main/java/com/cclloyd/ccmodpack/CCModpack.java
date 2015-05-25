package com.cclloyd.ccmodpack;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CCModpack.MODID, version = CCModpack.VERSION)
public class CCModpack {
	
    public static final String MODID = "ccmodpack";
    public static final String VERSION = "0.2";
	public static final String MCDIR = Minecraft.getMinecraft().mcDataDir.getAbsolutePath().substring(0, Minecraft.getMinecraft().mcDataDir.getAbsolutePath().length()-1);
	
    @Mod.Instance(CCModpack.MODID)
    public static CCModpack instance;
    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	CommonProxy.preInit(event);
    	ClientProxy.preInit(event);
    }
    
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	CommonProxy.init(event);
    	ClientProxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	CommonProxy.postInit(event);
    	ClientProxy.postInit(event);
    }
}