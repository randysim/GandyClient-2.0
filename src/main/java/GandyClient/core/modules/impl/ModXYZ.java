package GandyClient.core.modules.impl;

import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;
import net.minecraft.client.Minecraft;

public class ModXYZ extends ModDraggable {
	
	private String type = "Coordinates";
	private boolean isUseGl = false;
	private Setting settings;
	
	public ModXYZ () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
	}
	
	private String getXYZString () {
		return String.format("XYZ: %.0f / %.0f / %.0f", 
				Minecraft.getMinecraft().getRenderViewEntity().posX,
				Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().minY,
				Minecraft.getMinecraft().getRenderViewEntity().posZ
				);
	}	

	@Override
	public int getWidth() {
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth(getXYZString());
	}

	@Override
	public int getHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}

	@Override
	public String getDisplayName() {
		return type;
	}
	@Override 
	public String getSimpleName () {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(getXYZString(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
	}
	@Override
	public Setting getSettings() {
		return settings;
	}

}
