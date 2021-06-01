package GandyClient.modules.impl.togglesprintsneak;

import GandyClient.Constants;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;

public class ModToggleSprintSneak extends ModDraggable {
	
	private String type = "Toggle Sprint";
	private boolean isUseGl = false;
	private Setting settings;
	
	// settings
	public boolean flyBoost = true;
	public float flyBoostFactor = 1.1F;
	public int keyHoldTicks = 7;
	public boolean sprint = false;
	private String textToRender = "";
	
	public ModToggleSprintSneak () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("BACKGROUND")) {
			settings.updateSetting("BACKGROUND", 0);
		}
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		if (!settings.getSettings().containsKey("SIZE")) {
			settings.updateSetting("SIZE", Constants.FLOAT_SCALE);
		}
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return font.getStringWidth(textToRender);
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return font.FONT_HEIGHT;
	}
	
	@Override
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		// TODO Auto-generated method stub
		textToRender = ((GandyClientMovementInput) mc.thePlayer.movementInput).getDisplayText();
		font.drawStringWithShadow(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
	}
	@Override
	public void renderDummy (ScreenPosition pos) {
		textToRender = "[Toggle Sprint]";
		font.drawStringWithShadow(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
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
	
	public void setSprint (boolean isSprint) {
		this.sprint = isSprint;
	}

}
