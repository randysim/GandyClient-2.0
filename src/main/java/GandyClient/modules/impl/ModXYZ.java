package GandyClient.modules.impl;

import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;

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
				mc.getRenderViewEntity().posX,
				mc.getRenderViewEntity().getEntityBoundingBox().minY,
				mc.getRenderViewEntity().posZ
				);
	}	

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return font.getStringWidth(getXYZString());
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return font.FONT_HEIGHT;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		font.drawStringWithShadow(getXYZString(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
	}
	@Override
	public Setting getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}

}
