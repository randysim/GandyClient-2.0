package GandyClient.gui.elements;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import GandyClient.gui.GuiElement;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.gui.modmenu.ModMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class GuiBackButton implements GuiElement {
	
	private Minecraft mc;
	private FontRenderer font;
	private ScreenPosition pos;
	
	public GuiBackButton () {
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return font.getStringWidth("BACK");
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
		
		int optionWidth = font.getStringWidth("BACK");
		
		Gui.drawRect(
				(int)(pos.getAbsoluteX()/scaleFactor),
				(int) (pos.getAbsoluteY()/scaleFactor),
				(int) (pos.getAbsoluteX()/scaleFactor)+optionWidth,
				(int) (pos.getAbsoluteY()/scaleFactor)+this.getHeight(),
				new Color(0, 0, 0, 150).getRGB()
				);
		font.drawString("BACK", (int)(pos.getAbsoluteX()/scaleFactor), (int) (pos.getAbsoluteY()/scaleFactor), -1);
		GL11.glPopMatrix();
	}

	@Override
	public void onClick(int relativeX, int relativeY, ModMenuScreen reference) {
		// TODO Auto-generated method stub
		reference.setIsSetting(false);
	}

	@Override
	public void onDrag(int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(int relativeX, int relativeY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSettings() {
		// TODO Auto-generated method stub
		
	}

}
