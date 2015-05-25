package com.cclloyd.ccmodpack;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.cclloyd.ccmodpack.block.BlockCharcoal;
import com.cclloyd.ccmodpack.guihandler.GuiHandlerRefinedFurnace;
import com.cclloyd.ccmodpack.guihandler.GuiHandlerWirelessChest;

public class CommonProxy {
	
	public static Configuration config;
    public static CreativeTabCCModpack creativeTab;

	public static void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(new File(event.getModConfigurationDirectory(), "/ccmodpack.cfg"));
    	config.load();
    	
    	BlockRegistry.config(config);
    	BlockCharcoal.config(config);
    	if (config.hasChanged()) {
    		config.save();
    	}
    	NetworkRegistry.INSTANCE.registerGuiHandler(CCModpack.instance, GuiHandlerRegistry.getInstance());
    	
    	// Register GUI Handlers for all entity GUIs
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerWirelessChest(), GuiHandlerRegistry.GUI_WIRELESS_CHEST);
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerRefinedFurnace(), GuiHandlerRegistry.GUI_REFINED_FURNACE);
    
	}
	
	
	public static void init(FMLInitializationEvent event) {
		creativeTab = new CreativeTabCCModpack("ccmodpack");
    	
    	BlockRegistry.registerBlocks();
    	BlockRegistry.registerTileEntities();
    	Recipes.addRecipes();
    	Recipes.addSmelting();
    	
    	BlockRegistry.registerTools();
    	
	}
	
	
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
}
