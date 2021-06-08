package GandyClient.modules.impl;

import java.time.LocalTime;

import GandyClient.Constants;
import GandyClient.events.EventTarget;
import GandyClient.events.impl.ClientTickEvent;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;

public class ModTimeChanger extends ModDraggable {
	private String type = "Time Changer";
	private boolean isUseGl = false;
	private Setting settings;
	
	public ModTimeChanger () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		
		if (!settings.getSettings().containsKey("TIME")) {
			settings.updateSetting("TIME", (int) (0.01F * Constants.FLOAT_SCALE));
		}
		
		if (!settings.getSettings().containsKey("REAL_TIME")) {
			settings.updateSetting("REAL_TIME", 0);
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
