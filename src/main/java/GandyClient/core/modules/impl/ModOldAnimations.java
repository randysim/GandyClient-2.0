package GandyClient.core.modules.impl;

import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;

public class ModOldAnimations extends ModDraggable {

	private String type = "1.7 Animations";
	private boolean isUseGl = false;
	private Setting settings;
	
	public ModOldAnimations () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());

		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		
		if (!settings.getSettings().containsKey("BLOCK_HIT")) {
			settings.updateSetting("BLOCK_HIT", 0);
		}
		
		if (!settings.getSettings().containsKey("EATING_DRINKING")) {
			settings.updateSetting("EATING_DRINKING", 0);
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
		// TODO Auto-generated method stub


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
