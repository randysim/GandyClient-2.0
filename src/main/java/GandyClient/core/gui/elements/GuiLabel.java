package GandyClient.core.gui.elements;

import org.lwjgl.opengl.GL11;

import GandyClient.core.gui.GuiElement;
import GandyClient.core.gui.hud.ScreenPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class GuiLabel implements GuiElement {
	private Minecraft mc;
	private FontRenderer font;
	private ScreenPosition pos;
	private String label;
	
	public GuiLabel (String label) {
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.label = label;
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return font.getStringWidth(this.label);
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
		font.drawString(this.label, (int)(pos.getAbsoluteX()/scaleFactor), (int) (pos.getAbsoluteY()/scaleFactor), -1);
		GL11.glPopMatrix();
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
