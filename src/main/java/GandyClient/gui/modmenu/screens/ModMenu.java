package GandyClient.gui.modmenu.screens;

import java.awt.Color;
import java.util.HashMap;

import GandyClient.Constants;
import GandyClient.gui.ScreenContainer;
import GandyClient.gui.hud.HUDManager;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ModMenu extends ScreenContainer {
	
	public double modW = Constants.MOD_WIDTH, modH = Constants.MOD_HEIGHT;
	private Minecraft mc;
	
	public ModMenu(int cX, int cY, double cWidth, double cHeight, HashMap<ModDraggable, ScreenPosition> mods) {
		super(cX, cY, cWidth, cHeight);
		this.setComponents(mods);
		this.mc = Minecraft.getMinecraft();
	}
	
	public void drawScreen (GuiScreen mainScreen) {
		ScaledResolution res = new ScaledResolution(mc);
		
		mainScreen.drawRect(this.getX(), this.getY(), this.getX()+(int)(this.getWidth()*res.getScaledWidth()), this.getY()+(int) (this.getHeight()*res.getScaledHeight()), new Color(0, 0, 0, 110).getRGB());
		
		for (ModDraggable component : this.getComponents().keySet()) {
			ScreenPosition pos = this.getComponents().get(component);
			drawMod(mainScreen, component, pos.getAbsoluteX() + this.getX(), pos.getAbsoluteY() + this.getY());
		}
	}
	
	public void drawMod (GuiScreen mainScreen, ModDraggable mod, int x, int y) {
		int textWidth = mc.fontRendererObj.getStringWidth(mod.getDisplayName());
		
		mainScreen.drawRect(
				x, 
				y, 
				x + (int) (modW * this.getScaledWidth()), 
				y + (int) (modH * this.getScaledHeight()), 
				HUDManager.getInstance().has(mod) ? new Color(0, 255, 0, 102).getRGB() : new Color(255, 255, 255, 102).getRGB()
			);
		mc.fontRendererObj.drawString(mod.getDisplayName(), x+((int)(modW * this.getScaledWidth())/2)-(textWidth/2), y+((int)(modH * this.getScaledHeight())/2)-(mc.fontRendererObj.FONT_HEIGHT/2), -1);
		mainScreen.drawRect(
				x,
				y + (int) (modH * this.getScaledHeight()),
				x + (int) (modW * this.getScaledWidth()),
				y + (int) (modH * this.getScaledHeight() * 1.5),
				new Color(0, 0, 0, 102).getRGB()
				);
		textWidth = mc.fontRendererObj.getStringWidth("settings");
		mc.fontRendererObj.drawString("settings", x+((int)(modW * this.getScaledWidth())/2)-(textWidth/2), (y + (int) (modH * this.getScaledHeight())) + (int) ((modH/2 * this.getScaledHeight())/2)-(mc.fontRendererObj.FONT_HEIGHT/2), -1);
	}
	
}
