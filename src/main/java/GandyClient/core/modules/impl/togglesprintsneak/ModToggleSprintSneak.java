package GandyClient.core.modules.impl.togglesprintsneak;

import GandyClient.Constants;
import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;
import net.minecraft.client.Minecraft;

public class ModToggleSprintSneak extends ModDraggable {
	
	private String type = "Toggle Sprint";
	private boolean isUseGl = false;
	private Setting settings;
	
	// settings
	public boolean flyBoost = true;
	public float flyBoostFactor = 1.1F;
	public int keyHoldTicks = 7;
	public boolean sprint = false;
	private String textToRender = "[Sprinting]";
	
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
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth(textToRender);
	}

	@Override
	public int getHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}
	
	@Override
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
	}
	@Override
	public void renderDummy (ScreenPosition pos) {
		textToRender = "[Toggle Sprint]";
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
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
	public Setting getSettings() {
		return settings;
	}
	
	public void setSprint (boolean isSprint) {
		this.sprint = isSprint;
	}

}
