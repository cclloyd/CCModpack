package com.cclloyd.ccmodpack;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.cclloyd.ccmodpack.guihandler.GuiHandlerRefinedFurnace;
import com.cclloyd.ccmodpack.guihandler.GuiHandlerWirelessChest;
import com.cclloyd.ccmodpack.registry.BlockRegistry;
import com.cclloyd.ccmodpack.registry.ItemRegistry;
import com.cclloyd.ccmodpack.registry.Recipes;

public class CommonProxy {
	
	public static Configuration config;

    
    //private FMLControlledNamespacedRegistry<Item> iItemRegistry;
    //private Method addObjectRaw;
    
    
	public void preInit(FMLPreInitializationEvent event) {
		/*
		config = new Configuration(new File(event.getModConfigurationDirectory(), "/ccmodpack.cfg"));
    	config.load();
    	
    	BlockRegistry.config(config);
    	ItemRegistry.config(config);
    	if (config.hasChanged())
    		config.save();
    	*/
    	/*
    	try {
    	    Method getMain = GameData.class.getDeclaredMethod("getMain");
    	    getMain.setAccessible(true);
    	    GameData gameData = (GameData) getMain.invoke(null);

    	    Field f = GameData.class.getDeclaredField("iItemRegistry");
    	    f.setAccessible(true);
    	    iItemRegistry = (FMLControlledNamespacedRegistry<Item>) f.get(gameData);

    	    addObjectRaw = FMLControlledNamespacedRegistry.class.getDeclaredMethod("addObjectRaw", Integer.TYPE, Object.class, Object.class);
    	    addObjectRaw.setAccessible(true);

    	} catch (Exception e) {
    	    // TODO Auto-generated catch block
    	    e.printStackTrace();
    	}
    	*/
    	NetworkRegistry.INSTANCE.registerGuiHandler(CCModpack.instance, GuiHandlerRegistry.getInstance());
    
    	// Register GUI Handlers for all entity GUIs
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerWirelessChest(), GuiHandlerRegistry.GUI_WIRELESS_CHEST);
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerRefinedFurnace(), GuiHandlerRegistry.GUI_REFINED_FURNACE);
    
	}
	
	
	public void init(FMLInitializationEvent event) {
		
    	BlockRegistry.registerBlocks();
    	BlockRegistry.registerTileEntities();
    	
    	Recipes.addRecipes();
    	Recipes.addSmelting();
    	Recipes.addFurnaceRecipes();
    	ItemRegistry.registerTools();
    	
    	//FurnaceRecipes.addSmeltingRecipeForBlock(Blocks.glass, new ItemStack(BlockRegistry.cleanGlass, 1), .2F);
	}
	
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
