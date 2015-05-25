package com.cclloyd.ccmodpack;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabCCModpack extends CreativeTabs {
	
	
	public CreativeTabCCModpack(String label) {
		super(label);
	}

  @Override
  @SideOnly(Side.CLIENT)
  	public Item getTabIconItem() {
	  	return Item.getItemFromBlock(BlockRegistry.refinedFurnace);
  	}
  
  /*
  @SuppressWarnings("rawtypes")
  @Override
  public void displayAllReleventItems(List itemsToShowOnTab)
  {
    for (Object itemObject : Item.itemRegistry) {
      Item item = (Item)itemObject;
      if (item != null) {
        if (item.getUnlocalizedName().contains(".mbe")) {
          item.getSubItems(item, this, itemsToShowOnTab);  // add all sub items to the list
        }
      }
    }
  }
  */
}