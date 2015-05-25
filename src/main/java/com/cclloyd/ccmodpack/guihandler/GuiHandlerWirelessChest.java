package com.cclloyd.ccmodpack.guihandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.cclloyd.ccmodpack.container.ContainerWirelessChest;
import com.cclloyd.ccmodpack.gui.GuiWirelessChest;
import com.cclloyd.ccmodpack.tileentity.EntityWirelessChest;

public class GuiHandlerWirelessChest implements IGuiHandler{

	// Gets the server side element for the given gui id- this should return a container
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof EntityWirelessChest) {
			EntityWirelessChest wirelessChestEntity = (EntityWirelessChest) tileEntity;
			return new ContainerWirelessChest(player.inventory, wirelessChestEntity);
		}
		return null;
	}

	// Gets the client side element for the given gui id- this should return a gui
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof EntityWirelessChest) {
			EntityWirelessChest wirelessChestEntity = (EntityWirelessChest) tileEntity;
			return new GuiWirelessChest(player.inventory, wirelessChestEntity);
		}
		return null;
	}
}
