package GandyClient.core.modules.impl.autogg;

import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;

public class ModAutoGG extends ModDraggable {
	
	private String type = "AutoGG";
	private Setting settings;
	
	public ModAutoGG () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("SAY_L")) {
			settings.updateSetting("SAY_L", 0);
		}
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
	public ScreenPosition load() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(ScreenPosition pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Setting getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}

}
