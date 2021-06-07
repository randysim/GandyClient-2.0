package GandyClient.gui.elements;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import GandyClient.Constants;
import GandyClient.gui.GuiElement;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.gui.modmenu.ModMenuScreen;
import GandyClient.modules.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class GuiSlider implements GuiElement {
	private ScreenPosition pos;
	private String optionDesc;
	private Minecraft mc;
	private FontRenderer font;
	private String mod;
	private String option;
	private float minValue;
	private float maxValue;
	private float currentValue;
	
	
	public GuiSlider (String optionDesc, String mod, String option, float minValue, float maxValue) {
		this.optionDesc = optionDesc;
		this.mod = mod;
		this.option = option;
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.currentValue = (float)SettingsManager.getInstance().getSettingValue(mod, option)/(float)Constants.FLOAT_SCALE;
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return Math.max(font.getStringWidth(optionDesc + ": " + this.currentValue), (int) (maxValue * 50));
	}
	
	public int getSliderWidth () {
		return (int) (maxValue * 50);
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return (int) (this.getSliderHeight() + (font.FONT_HEIGHT * 3/2));
	}
	
	public int getSliderHeight () {
		return font.FONT_HEIGHT;
	}

	@Override
	public ScreenPosition load() {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public void save(ScreenPosition pos) {
		// TODO Auto-generated method stub
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
		
		font.drawString(optionDesc + ": " + this.currentValue, (int)(pos.getAbsoluteX()/scaleFactor), (int) (pos.getAbsoluteY()/scaleFactor), -1);
		
		Gui.drawRect(
				Math.max((int)((this.currentValue-this.minValue) * 50) + this.getSliderHeight(), (int) (pos.getAbsoluteX()/scaleFactor)), 
				(int) (pos.getAbsoluteY()/scaleFactor) + (this.getSliderHeight()/4) + font.FONT_HEIGHT * 3/2,
				(int)(pos.getAbsoluteX()/scaleFactor) + this.getSliderWidth(), 
				(int) (pos.getAbsoluteY()/scaleFactor) + ((this.getSliderHeight()/4) * 3) + font.FONT_HEIGHT * 3/2, 
				new Color(255, 0, 0, 150).getRGB()
				); // slider background (red)
		Gui.drawRect(
				(int)(pos.getAbsoluteX()/scaleFactor), 
				(int) (pos.getAbsoluteY()/scaleFactor) + (this.getSliderHeight()/4) + font.FONT_HEIGHT * 3/2,
				(int)(pos.getAbsoluteX()/scaleFactor) + (int)((this.currentValue-this.minValue) * 50), 
				(int) (pos.getAbsoluteY()/scaleFactor) + ((this.getSliderHeight()/4) * 3) + font.FONT_HEIGHT * 3/2, 
				new Color(0, 255, 0).getRGB()
				); // slider background (green)
		Gui.drawRect(
				(int)(pos.getAbsoluteX()/scaleFactor) + (int)((this.currentValue-this.minValue) * 50), 
				(int) (pos.getAbsoluteY()/scaleFactor) + font.FONT_HEIGHT * 3/2,
				(int)(pos.getAbsoluteX()/scaleFactor) + (int)((this.currentValue-this.minValue) * 50) + this.getSliderHeight(), 
				(int) (pos.getAbsoluteY()/scaleFactor) + this.getSliderHeight() + font.FONT_HEIGHT * 3/2, 
				new Color(255, 255, 255).getRGB()
				); // slider
		GL11.glPopMatrix();
	}

	@Override
	public void onClick(int relativeX, int relativeY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrag(int newX, int newY) {
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

		int width = this.getSliderWidth();
		
		float currentFloat = (float)((newX-pos.getAbsoluteX())/scaleFactor)/(float)width;
		currentFloat *= maxValue;
	
		this.currentValue = Math.max(minValue, Math.min(currentFloat, maxValue));
	}

	@Override
	public void onClick(int relativeX, int relativeY, ModMenuScreen reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSettings() {
		// TODO Auto-generated method stub
		if (this.mod == null) return;
		SettingsManager.getInstance().updateSetting(this.mod, this.option, (int)(Constants.FLOAT_SCALE * this.currentValue));
	}
	
	public float getValue () {
		return this.currentValue;
	}

}
