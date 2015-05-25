package com.cclloyd.ccmodpack.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.cclloyd.ccmodpack.container.ContainerWirelessChest;
import com.cclloyd.ccmodpack.tileentity.EntityWirelessChest;

@SideOnly(Side.CLIENT)
public class GuiWirelessChest extends GuiContainer {
	
	/**
	 * GuiInventoryBasic is a simple gui that does nothing but draw a background image and a line of text on the screen
	 * everything else is handled by the vanilla container code
	 */
	private static final ResourceLocation texture = new ResourceLocation("ccmodpack", "textures/gui/container/wirelessChest.png");
	@SuppressWarnings("unused")
	private EntityWirelessChest wirelessChestEntity;
	private ContainerWirelessChest container;
	
	
	public GuiWirelessChest(InventoryPlayer invPlayer, EntityWirelessChest tile) {
		super(new ContainerWirelessChest(invPlayer, tile));
		wirelessChestEntity = tile;
		// Set the width and height of the gui.  Should match the size of the texture!
		xSize = 176;
		ySize = 184;
		this.xSize = 176;
		this.ySize = 184;
		
		this.container = (ContainerWirelessChest)this.inventorySlots;
	}


	public Container getContainer() {
		return this.container;
	}
	
	// draw the background for the GUI - rendered first
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
		// Bind the image texture of our custom container
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	// draw the foreground for the GUI - rendered after the slots, but before the dragged items and tooltips
	// renders relative to the top left corner of the background
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		//final int LABEL_XPOS = this.xSize / 2 - 10;
		//final int LABEL_YPOS = 5;
		//this.guiLeft = (this.width - this.xSize) / 2;
        //this.guiTop = (this.height - this.ySize) / 2;
		//int posX = this.guiLeft;
		//int posY = this.guiTop;
		//int center = this.xSize / 2 + this.guiLeft;
		//fontRendererObj.drawString("0000", this.guiLeft / 2, 4, Color.darkGray.getRGB());

	}

	
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();
        this.mc.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
		int posX = this.guiLeft;
		int posY = this.guiTop;
		int center = this.xSize / 2 + posX;
		
		int width1000 = 40;
		int width100 = 33;
		int width10 = 28;
		int width1 = 20;
		
		int posX1000 = 50;
		int posX100 = 67;
		int posX10 = 36;
		int posX1 = 11;

		int center1000 = center - (width1000 / 2);
		int center100 = center - (width100 / 2);
		int center10 = center - (width10 / 2);
		int center1 = center - (width1 / 2);
		int posY1000 = posY + 4;
		int posY100 = posY + 25;
		int posY10 = posY100;
		int posY1 = posY100;
		
		this.buttonList.add(new GuiButton(1, center1000 - posX1000, posY1000, width1000, 20, "-1000"));
		this.buttonList.add(new GuiButton(2, center1000 + posX1000, posY1000, width1000, 20, "+1000"));
		
		this.buttonList.add(new GuiButton(3, center100 - posX100, posY100, width100, 20, "-100"));
		this.buttonList.add(new GuiButton(4, center100 + posX100, posY100, width100, 20, "+100"));
		
		this.buttonList.add(new GuiButton(5, center10 - posX10, posY10, width10, 20, "-10"));
		this.buttonList.add(new GuiButton(6, center10 + posX10, posY10, width10, 20, "+10"));
		
		this.buttonList.add(new GuiButton(7, center1 - posX1, posY1, width1, 20, "-1"));
		this.buttonList.add(new GuiButton(8, center1 + posX1, posY1, width1, 20, "+1"));

		this.container.detectAndSendChanges();
		this.buttonList.add(new DisabledButton(0, center - (45 / 2), posY1000, 45, 20, container.getFreqStr()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void actionPerformed(GuiButton button) {
		this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
		int posX = this.guiLeft;
		int posY = this.guiTop;
		int center = this.xSize / 2 + posX;
		int posY1000 = posY + 4;
		

		if (button.id == 1) {
			if (container.getFreq() < 1000)
				container.setFreq(0);
			else
				container.setFreq(container.getFreq() - 1000);
			
		}
		if (button.id == 2) {
			if (container.getFreq() > 8999)
				container.setFreq(9999);
			else
				container.setFreq(container.getFreq() + 1000);
		}
		
		
		this.buttonList.remove(this.buttonList.size() - 1);
		this.buttonList.add(new DisabledButton(0, center - (45 / 2), posY1000, 45, 20, container.getFreqStr()));
		
		container.updateProgressBar(0,  container.getFreq());
		this.mc.playerController.sendEnchantPacket(this.container.windowId, container.getFreq());
	}
	
	@Override
	public void onGuiClosed() {
		FMLLog.getLogger().debug(DimensionManager.getCurrentSaveRootDirectory());

        if (this.mc.thePlayer != null)
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);
    }
	
}
