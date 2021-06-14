package GandyClient.core.modules.impl;

import GandyClient.Constants;
import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;
import net.minecraft.client.Minecraft;

public class ModFPS extends ModDraggable {
	
	private String type = "FPS";
	private boolean isUseGl = false;
	private Setting settings;
	
	public ModFPS () {
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
		return 50;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}
	
	@Override
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("[" + Minecraft.getDebugFPS() + " FPS]", pos.getAbsoluteX(),  pos.getAbsoluteY(), -1);
		
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
