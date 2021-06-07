package GandyClient.gui.elements;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import GandyClient.gui.GuiElement;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.gui.modmenu.ModMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class GuiColorPicker implements GuiElement {
	private Minecraft mc;
	private FontRenderer font;
	private ScreenPosition pos;
	private int width = 250;
	private int height;
	private String mod;
	private GuiSlider red;
	private GuiSlider blue;
	private GuiSlider green;
	
	public GuiColorPicker (String modName) {
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.mod = modName;
		
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
		
		this.red = new GuiSlider("Red", modName, "COLOR_RED", 0.1F, 1.0F);
		this.blue = new GuiSlider("Green", modName, "COLOR_GREEN", 0.1F, 1.0F);
		this.green = new GuiSlider("Blue", modName, "COLOR_BLUE", 0.1F, 1.0F);
		
		this.height = font.FONT_HEIGHT + red.getHeight() + blue.getHeight() + (int)(green.getHeight() * 1.5);
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public ScreenPosition load() {
		// TODO Auto-generated method stub
		return pos;
	}
	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
	}

	@Override
	public void render(ScreenPosition pos) {
		// TODO Auto-generated method stub
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
		GL11.glPushMatrix();
		GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
		/* ============================== */
		
		// draw background box
		
		Gui.drawRect((int)(pos.getAbsoluteX()/scaleFactor), (int) (pos.getAbsoluteY()/scaleFactor), (int)(pos.getAbsoluteX()/scaleFactor) + this.getWidth(), (int) (pos.getAbsoluteY()/scaleFactor) + this.getHeight(), new Color(0, 0, 0, 200).getRGB());
		
		// draw color demo
		
		Color demo = new Color((int) (this.red.getValue() * 255), (int) (this.blue.getValue() * 255), (int) (this.green.getValue() * 255));
		Gui.drawRect((int)(pos.getAbsoluteX()/scaleFactor) + this.getWidth() - 50 - 5, (int) (pos.getAbsoluteY()/scaleFactor) + (this.getHeight()/2) - 25, (int)(pos.getAbsoluteX()/scaleFactor) + this.getWidth() - 5, (int) (pos.getAbsoluteY()/scaleFactor) + (this.getHeight()/2) + 25, demo.getRGB());
		
		// draw label
		
		font.drawString("RGB Color", (int)(pos.getAbsoluteX()/scaleFactor) + ((this.getWidth()/2) - (font.getStringWidth("RGB Color")/2)), (int) (pos.getAbsoluteY()/scaleFactor), -1);
		
		GL11.glPopMatrix();
		
		// draw sliders
		if (red.load() == null)	red.save(ScreenPosition.fromAbsolute(
				pos.getAbsoluteX() + 20, 
				pos.getAbsoluteY() + (int) ((font.FONT_HEIGHT) * scaleFactor)
			));
		if (blue.load() == null) blue.save(ScreenPosition.fromAbsolute(
				pos.getAbsoluteX() + 20, 
				pos.getAbsoluteY() + (int) ((font.FONT_HEIGHT + red.getHeight()) * scaleFactor)
		));
		if (green.load() == null) green.save(ScreenPosition.fromAbsolute(
				pos.getAbsoluteX() + 20, 
				pos.getAbsoluteY() + (int) ((font.FONT_HEIGHT + red.getHeight() + blue.getHeight()) * scaleFactor)
			));
		
		red.render(red.load());
		blue.render(blue.load());
		green.render(green.load());
		
		
		/* ============================== */
		
	}

	@Override
	public void onClick(int newX, int newY, ModMenuScreen reference) {

	}

	@Override
	public void onDrag(int newX, int newY) {
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
		
		if (newX >= red.load().getAbsoluteX() && newX <= red.load().getAbsoluteX() + (red.getWidth() * scaleFactor)) {
			if (newY >= red.load().getAbsoluteY() && newY <= red.load().getAbsoluteY() + (red.getHeight() * scaleFactor)) {
				red.onDrag(newX, newY);
			} else if (newY >= blue.load().getAbsoluteY() && newY <= blue.load().getAbsoluteY() + (blue.getHeight() * scaleFactor)) {
				blue.onDrag(newX, newY);
			} else if (newY >= green.load().getAbsoluteY() && newY <= green.load().getAbsoluteY() + (green.getHeight() * scaleFactor)) {
				green.onDrag(newX, newY);
			}
		} 
	}

	@Override
	public void saveSettings() {
		// TODO Auto-generated method stub
		red.saveSettings();
		blue.saveSettings();
		green.saveSettings();
	}

	@Override
	public void onClick(int relativeX, int relativeY) {
		// TODO Auto-generated method stub
		
	}
}
