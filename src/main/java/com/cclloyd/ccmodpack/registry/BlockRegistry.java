package com.cclloyd.ccmodpack.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.cclloyd.ccmodpack.block.*;
import com.cclloyd.ccmodpack.tileentity.EntityRefinedFurnace;
import com.cclloyd.ccmodpack.tileentity.EntityWirelessChest;


public class BlockRegistry extends CommonRegistry {
		
	// Initialize Blocks
	public static Block blockCharcoal = new BlockCharcoal();
	public static Block blockPoweredRedstoneLantern = new BlockPoweredRedstoneLantern();
	public static Block blockDust;
	public static Block cleanGlass = new BlockCleanGlass(false);
	public static Block cleanGlassPane = new BlockCleanGlassPane(false);
	public static Block cleanGlassTempered = new BlockCleanGlass(true);
	public static Block cleanGlassPaneTempered = new BlockCleanGlassPane(true);
	public static Block temperedObsidian = new BlockTemperedObsidian();
	
	// Slabs
	public static Block blockSlabGranite = new BlockSlabGraniteHalf();
	public static Block blockSlabGraniteDouble = new BlockSlabGraniteDouble();
	public static Block blockSlabAndesite = new BlockSlabAndesiteHalf();
	public static Block blockSlabAndesiteDouble = new BlockSlabAndesiteDouble();
	public static Block blockSlabDiorite = new BlockSlabDioriteHalf();
	public static Block blockSlabDioriteDouble = new BlockSlabDioriteDouble();
	
	// Stairs
	public static Block blockStairsGranite = new BlockStairsGranite();
	public static Block blockStairsDiorite = new BlockStairsDiorite();
	public static Block blockStairsAndesite = new BlockStairsAndesite();

	// Walls
	public static Block blockWallGranite = new BlockWallGranite();
	
	// Initialize Blocks as Items
	public static Item blockCharcoalItem = Item.getItemFromBlock(blockCharcoal);
	public static Item blockPoweredRedstoneLanternItem = Item.getItemFromBlock(blockPoweredRedstoneLantern);
	public static Item itemDust = Item.getItemFromBlock(blockDust);
	
	// Initialize Tile Entities
	public static TileEntity wirelessChestEntity = new EntityWirelessChest();
	public static TileEntity entityRefinedFurnace = new EntityRefinedFurnace();
	
	// Initialize Tile Entity Blocks
	public static Block wirelessChest = new BlockWirelessChest();
	public static Block refinedFurnace = new BlockRefinedFurnace();

	
	
	
	public static void registerBlocks() {	
		// Register Blocks
		GameRegistry.registerBlock(blockCharcoal, BlockCharcoal.name);
		GameRegistry.registerBlock(blockPoweredRedstoneLantern, BlockPoweredRedstoneLantern.name);
		GameRegistry.registerBlock(temperedObsidian, BlockTemperedObsidian.name);
		GameRegistry.registerBlock(cleanGlass, BlockCleanGlass.name);
		GameRegistry.registerBlock(cleanGlassPane, BlockCleanGlassPane.name);
		GameRegistry.registerBlock(cleanGlassTempered, BlockCleanGlass.name_tempered);
		GameRegistry.registerBlock(cleanGlassPaneTempered, BlockCleanGlassPane.name_tempered);
		
		// Register Slabs
		GameRegistry.registerBlock(blockSlabGranite, ItemBlockSlabGranite.class, BlockSlabGraniteHalf.name, 
									blockSlabGranite, blockSlabGraniteDouble, false);
		GameRegistry.registerBlock(blockSlabGraniteDouble, ItemBlockSlabGranite.class, BlockSlabGraniteDouble.name, 
									blockSlabGranite, blockSlabGraniteDouble, true);
		GameRegistry.registerBlock(blockSlabAndesite, ItemBlockSlabAndesite.class, BlockSlabAndesiteHalf.name, 
									blockSlabAndesite, blockSlabAndesiteDouble, false);
		GameRegistry.registerBlock(blockSlabAndesiteDouble, ItemBlockSlabAndesite.class, BlockSlabAndesiteDouble.name, 
									blockSlabAndesite, blockSlabAndesiteDouble, true);
		GameRegistry.registerBlock(blockSlabDiorite, ItemBlockSlabDiorite.class, BlockSlabDioriteHalf.name, 
									blockSlabDiorite, blockSlabDioriteDouble, false);
		GameRegistry.registerBlock(blockSlabDioriteDouble, ItemBlockSlabDiorite.class, BlockSlabDioriteDouble.name, 
									blockSlabDiorite, blockSlabDioriteDouble, true);
		
		// Register Stairs
		GameRegistry.registerBlock(blockStairsGranite, BlockStairsGranite.name);
		GameRegistry.registerBlock(blockStairsAndesite, BlockStairsAndesite.name);
		GameRegistry.registerBlock(blockStairsDiorite, BlockStairsDiorite.name);
		
		// Register Walls
		//GameRegistry.registerBlock(blockWallGranite, BlockWallGranite.name);
		
		/*
		try {
			GameRegistry.addSubstitutionAlias("minecraft:glass", GameRegistry.Type.BLOCK, blockBorderlessGlass);
			GameRegistry.addSubstitutionAlias("minecraft:glass", GameRegistry.Type.ITEM, Item.getItemFromBlock(blockBorderlessGlass));
			//GameRegistry.addSubstitutionAlias("minecraft:glass", GameRegistry.Type.ITEM, new ItemMultiTexture(blockBorderlessGlass, mySand, new String[] { "default", "red", "my" }).setUnlocalizedName("sand"));
			//GameRegistry.addSubstitutionAlias("minecraft:sand", GameRegistry.Type.ITEM, new ItemMultiTexture(mySand, mySand, new String[] { "default", "red", "my" }).setUnlocalizedName("sand"));

		} catch (ExistingSubstitutionException e) {
			e.printStackTrace();
		}*/
	}
	
	
	public static void registerTileEntities() {
		// Register Tile Entities
		//GameRegistry.registerTileEntity(EntityWirelessChest.class, EntityWirelessChest.name);
		GameRegistry.registerTileEntity(EntityRefinedFurnace.class, EntityRefinedFurnace.name);
		
		// Register Tile Entity Blocks
		//GameRegistry.registerBlock(wirelessChest, BlockWirelessChest.name);
		GameRegistry.registerBlock(refinedFurnace, BlockRefinedFurnace.name);
	}
	
	
	
	public static void registerRenders() {
		// Register block renders
		addRender(blockCharcoal, BlockCharcoal.name);
		addRender(blockPoweredRedstoneLantern, BlockPoweredRedstoneLantern.name);
		addRender(temperedObsidian, BlockTemperedObsidian.name);
		addRender(cleanGlass, BlockCleanGlass.name);
		addRender(cleanGlassPane, BlockCleanGlassPane.name);
		addRender(cleanGlassTempered, BlockCleanGlass.name_tempered);
		addRender(cleanGlassPaneTempered, BlockCleanGlassPane.name_tempered);
		
		addRender(wirelessChest, BlockWirelessChest.name);
		addRender(refinedFurnace, BlockRefinedFurnace.name);
		
		
		addRender(blockSlabGranite, BlockSlabGraniteHalf.name);
		addRender(blockSlabDiorite, BlockSlabDioriteHalf.name);
		addRender(blockSlabAndesite, BlockSlabAndesiteHalf.name);
		addRender(blockStairsGranite, BlockStairsGranite.name);
		addRender(blockStairsAndesite, BlockStairsAndesite.name);
		addRender(blockStairsDiorite, BlockStairsDiorite.name);
	      
	}
	
}
