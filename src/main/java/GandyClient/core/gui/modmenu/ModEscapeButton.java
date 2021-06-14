package GandyClient.core.gui.modmenu;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ModEscapeButton {
	private String type;
	private boolean isHovered = false;

	public ModEscapeButton (String type) {
		this.type = type;
	}
	
	public String getType () {
		return this.type;
	}
	
	public void setHovered (boolean isHovered) {
		this.isHovered = isHovered;
	}
	public boolean getHovered () {
		return isHovered;
	}
	
	public void draw (GuiScreen screen, int x, int y, int width, int height) {
		Minecraft mc = Minecraft.getMinecraft();
		int textWidth = mc.fontRendererObj.getStringWidth(type);
		screen.drawRect(
				x, 
				y, 
				x + width, 
				y + height, 
				new Color(0, 0, 0, 150).getRGB()
			);
		mc.fontRendererObj.drawString(
				type, 
				x+(width/2)-(textWidth/2), 
				y+(height/2)-(mc.fontRendererObj.FONT_HEIGHT/2), 
				-1 // white
				);
		
	}
}
