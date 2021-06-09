package GandyClient.gui.elements;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import GandyClient.gui.GuiElement;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class GuiCheckBox implements GuiElement {
	private ScreenPosition pos;
	private int checked = 0;
	private String optionDesc;
	private Minecraft mc;
	private FontRenderer font;
	private String mod;
	private String option;
	
	public GuiCheckBox (String optionDesc, String mod, String option) {
		this.optionDesc = optionDesc;
		this.mod = mod;
		this.option = option;
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.checked = SettingsManager.getInstance().getSettingValue(mod, option);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return font.getStringWidth(optionDesc) + (font.FONT_HEIGHT * 2);
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return font.FONT_HEIGHT;
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
	public void render (ScreenPosition pos) {
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
		
		int optionWidth = font.getStringWidth(this.optionDesc);
		
		Gui.drawRect(
				(int)(pos.getAbsoluteX()/scaleFactor) + optionWidth + font.FONT_HEIGHT, 
				(int) (pos.getAbsoluteY()/scaleFactor),
				(int)(pos.getAbsoluteX()/scaleFactor) + optionWidth + (font.FONT_HEIGHT * 2), 
				(int) (pos.getAbsoluteY()/scaleFactor) + font.FONT_HEIGHT, 
				checked == 1 ? new Color(0, 255, 0, 150).getRGB() : new Color(255, 0, 0, 150).getRGB()
				);
		font.drawString(optionDesc, (int) (pos.getAbsoluteX()/scaleFactor), (int) (pos.getAbsoluteY()/scaleFactor), -1);	
		
		GL11.glPopMatrix();
		this.saveSettings();
	}

	@Override
	public void onClick(int relativeX, int relativeY) {
		// TODO Auto-generated method stub
		checked = checked == 1 ? 0 : 1;
		SettingsManager.getInstance().updateSetting(this.mod, this.option, this.checked);
	}

	@Override
	public void onDrag(int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSettings() {
		// TODO Auto-generated method stub
		SettingsManager.getInstance().updateSetting(this.mod, this.option, this.checked);
	}

}
