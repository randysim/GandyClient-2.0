package GandyClient.gui.modmenu.screens;

import java.awt.Color;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import GandyClient.gui.GuiElement;
import GandyClient.gui.ScreenContainer;
import GandyClient.gui.elements.GuiBackButton;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.gui.modmenu.ModMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class SettingsScreen extends ScreenContainer {
	
	private HashMap<GuiElement, ScreenPosition> elements = new HashMap<GuiElement, ScreenPosition>();
	private String name;
	private int navBarHeight = 0;
	private Minecraft mc;
	private ModMenuScreen reference;
	private int topPadding = 5;
	private GuiElement selectedElement;
	
	public SettingsScreen(int cX, int cY, double cWidth, double cHeight, String name) {
		super(cX, cY, cWidth, cHeight);
		this.name = name;
		this.mc = Minecraft.getMinecraft();
		this.elements.put(new GuiBackButton(), ScreenPosition.fromAbsolute(5, topPadding));
		this.navBarHeight = mc.fontRendererObj.FONT_HEIGHT;
	}
	
	public void drawScreen (ModMenuScreen mainScreen) {
		if (mainScreen == null) return;
		if (this.reference == null) this.reference = mainScreen;
		
		ScaledResolution res = new ScaledResolution(mc);
		mainScreen.drawRect(this.getX(), this.getY(), this.getX()+(int)(this.getWidth()*res.getScaledWidth()), this.getY()+(int) (this.getHeight()*res.getScaledHeight()), new Color(0, 0, 0, 110).getRGB());
		
		// draw top
		int headerWidth = mc.fontRendererObj.getStringWidth(this.name);
		double scaleFactor = this.getScale();
		GL11.glPushMatrix();
		GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
		mc.fontRendererObj.drawString(this.name, (int)(((0.5*this.getScaledWidth()) - (headerWidth/2))/scaleFactor), topPadding, -1);
		GL11.glPopMatrix();
		for (GuiElement gElement : elements.keySet()) {
			ScreenPosition pos = elements.get(gElement);
			gElement.render(pos);
		}
	}
	
	public void addComponents (GuiElement... gElements){
		int currentY = navBarHeight;
		int count = 1;
		
		for (GuiElement gElement : gElements) {
			elements.put(
					gElement, 
					ScreenPosition.fromAbsolute(
							this.getX() + (int)(this.getScaledWidth()/20), 
							this.getY() + currentY + ((int)(this.getScaledHeight()/20)*count) + topPadding
							)
					);
			count++;
			currentY += gElement.getHeight();
		}
	}
	
	public double getScale () {
		int guiScale = mc.gameSettings.guiScale;
		double scaleFactor = 1;
		
		switch (guiScale) {
		case 1:
			scaleFactor = 4;
			break;
		case 2:
			scaleFactor = 2;
			break;
		case 3:
			scaleFactor = 1.33;
			break;
		}
		
		return scaleFactor;
	}
	
	public GuiElement loadMouseOver (int mouseX, int mouseY) {
		double scaleFactor = getScale();
		
		for (GuiElement gElement : elements.keySet()) {
			ScreenPosition pos = elements.get(gElement);
			
			if (
					(mouseX/scaleFactor) >= (pos.getAbsoluteX()/scaleFactor)+this.getX() &&
					(mouseX/scaleFactor) <= (pos.getAbsoluteX()/scaleFactor)+this.getX()+gElement.getWidth() &&
					(mouseY/scaleFactor) >= (pos.getAbsoluteY()/scaleFactor) + this.getY() &&
					(mouseY/scaleFactor) <= (pos.getAbsoluteY()/scaleFactor) + this.getY()+gElement.getHeight()
			) {
				return gElement;
			}
		}
		
		return null;
	}
	
	public void onClick (int mouseX, int mouseY) {
		GuiElement clickedElement = loadMouseOver(mouseX, mouseY);
		if (clickedElement != null) {
			ScreenPosition pos = elements.get(clickedElement);
			double scaleFactor = getScale();
			if (clickedElement instanceof GuiBackButton) {
				clickedElement.onClick(mouseX-(int)((pos.getAbsoluteX()/scaleFactor)+this.getX()), mouseY - (int)((pos.getAbsoluteY()/scaleFactor) + this.getY()), this.reference);
			} else {
				clickedElement.onClick(mouseX-(int)((pos.getAbsoluteX()/scaleFactor)+this.getX()), mouseY - (int)((pos.getAbsoluteY()/scaleFactor) + this.getY()));
			}
		}
		
		this.selectedElement = clickedElement;
	}
	
	public void mouseClickMove (int mouseX, int mouseY) {
		if (selectedElement != null) {
			ScreenPosition pos = elements.get(selectedElement);
			int x = pos.getAbsoluteX();
			int y = pos.getAbsoluteY();
			double scaleFactor = getScale();
			selectedElement.save(pos);
			selectedElement.onDrag(mouseX, mouseY);
		}
	}
	
	public void onClose () {
		for (GuiElement element : elements.keySet()) {
			element.saveSettings();
		}
	}
	
	public String getName () {
		return this.name;
	}
}
