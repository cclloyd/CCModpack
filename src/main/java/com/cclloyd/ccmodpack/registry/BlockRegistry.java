package com.cclloyd.ccmodpack.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.cclloyd.ccmodpack.block.BlockBorderlessGlass;
import com.cclloyd.ccmodpack.block.BlockCharcoal;
import com.cclloyd.ccmodpack.block.BlockPoweredRedstoneLantern;
import com.cclloyd.ccmodpack.block.BlockRefinedFurnace;
import com.cclloyd.ccmodpack.block.BlockSlabAndesiteDouble;
import com.cclloyd.ccmodpack.block.BlockSlabAndesiteHalf;
import com.cclloyd.ccmodpack.block.BlockSlabDioriteDouble;
import com.cclloyd.ccmodpack.block.BlockSlabDioriteHalf;
import com.cclloyd.ccmodpack.block.BlockSlabGraniteDouble;
import com.cclloyd.ccmodpack.block.BlockSlabGraniteHalf;
import com.cclloyd.ccmodpack.block.BlockStairsAndesite;
import com.cclloyd.ccmodpack.block.BlockStairsDiorite;
import com.cclloyd.ccmodpack.block.BlockStairsGranite;
import com.cclloyd.ccmodpack.block.BlockWallGranite;
import com.cclloyd.ccmodpack.block.BlockWirelessChest;
import com.cclloyd.ccmodpack.block.ItemBlockSlabAndesite;
import com.cclloyd.ccmodpack.block.ItemBlockSlabDiorite;
import com.cclloyd.ccmodpack.block.ItemBlockSlabGranite;
import com.cclloyd.ccmodpack.tileentity.EntityRefinedFurnace;
import com.cclloyd.ccmodpack.tileentity.EntityWirelessChest;


public class BlockRegistry extends CommonRegistry {
		
	// Initialize Blocks
	public static Block blockCharcoal = new BlockCharcoal();
	public static Block blockPoweredRedstoneLantern = new BlockPoweredRedstoneLantern();
	public static Block blockDust;
	public static Block blockBorderlessGlass = new BlockBorderlessGlass();
	
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
		//GameRegistry.registerBlock(blockBorderlessGlass, BlockBorderlessGlass.name);
		
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
