package GandyClient.modules.impl;

import GandyClient.Constants;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;

public class ModSettings extends ModDraggable {
	private String type = "Settings";
	private boolean isUseGl = false;
	private Setting settings;
	
	public ModSettings () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		
		if (!settings.getSettings().containsKey("COLOR_RED")) {
			settings.updateSetting("COLOR_RED", Constants.FLOAT_SCALE);
		}
		if (!settings.getSettings().containsKey("COLOR_BLUE")) {
			settings.updateSetting("COLOR_BLUE", Constants.FLOAT_SCALE);
		}
		if (!settings.getSettings().containsKey("COLOR_GREEN")) {
			settings.updateSetting("COLOR_GREEN", Constants.FLOAT_SCALE);
		}
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		
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
	public Setting getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}
}
