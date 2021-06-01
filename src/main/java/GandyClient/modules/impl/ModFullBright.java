package GandyClient.modules.impl;

import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;

public class ModFullBright extends ModDraggable {
	private String type = "Fullbright";
	private Setting settings;
	
	public ModFullBright () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
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
		return true;
	}

	@Override
	public void render(ScreenPosition pos) {
		// TODO Auto-generated method stub
		if (mc.gameSettings.gammaSetting != 100f) {
			mc.gameSettings.gammaSetting = 100f;
		}
	}

	@Override
	public Setting getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}

}
