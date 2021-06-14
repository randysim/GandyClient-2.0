package GandyClient.core.modules.impl.itemphysics;

import GandyClient.Constants;
import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;

public class ModItemPhysics extends ModDraggable {
	private String type = "Item Physics";
	private boolean isUseGl = false;
	private Setting settings;
	
	public ModItemPhysics () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());

		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		if (!settings.getSettings().containsKey("ROTATION_SPEED")) {
			settings.updateSetting("ROTATION_SPEED", (int) (0.5F * (float) Constants.FLOAT_SCALE));
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
