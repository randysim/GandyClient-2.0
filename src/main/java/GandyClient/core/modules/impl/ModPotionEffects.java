package GandyClient.core.modules.impl;

import java.util.ArrayList;

import GandyClient.Constants;
import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ModPotionEffects extends ModDraggable {
	
	private String type = "Potion Effects";
	private boolean isUseGl = false;
	private Minecraft mc = Minecraft.getMinecraft();
	private int width, height;
	private Setting settings;
	
	private ArrayList<String> getPotionEffects () {
		ArrayList<String> potionEffects = new ArrayList<String>();
		for (PotionEffect effect : mc.thePlayer.getActivePotionEffects()) {
			Potion potion = Potion.potionTypes[effect.getPotionID()];
			String effectName = I18n.format(potion.getName());
			String amplifier = "";
			int durationTicks = effect.getDuration();
			int durationSeconds = durationTicks / 20;
			int durationMinutes = durationSeconds / 60;
			durationSeconds -= durationMinutes * 60;
			
			if (effect.getAmplifier() == 1)
            {
                amplifier = "II";
            }
            else if (effect.getAmplifier() == 2)
            {
            	amplifier = "III";
            }
            else if (effect.getAmplifier() == 3)
            {
            	amplifier = "IV";
            }
            else if (effect.getAmplifier() == 4)
            {
            	amplifier = "V";
            }
            else if (effect.getAmplifier() == 5)
            {
            	amplifier = "VI";
            }
            else if (effect.getAmplifier() == 6)
            {
            	amplifier = "VII";
            }
            else if (effect.getAmplifier() == 7)
            {
            	amplifier = "VIII";
            }
            else if (effect.getAmplifier() == 8)
            {
            	amplifier = "IX";
            }
            else if (effect.getAmplifier() == 9)
            {
            	amplifier = "X";
            } else if (effect.getAmplifier() > 9) {
               	amplifier =  "" + (effect.getAmplifier() + 1);        
          	}
			
			String potionName = effectName + " " + amplifier;
			potionEffects.add(potionName + " " + durationMinutes + ":" + (durationSeconds > 9 ? "" : "0") + durationSeconds);
		}
		return potionEffects;
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	public ModPotionEffects () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		if (!settings.getSettings().containsKey("SIZE")) {
			settings.updateSetting("SIZE", Constants.FLOAT_SCALE);
		}
	}

	@Override
	public int getHeight() {
		return height;
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
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		ArrayList<String> potionEffects = getPotionEffects();
		int count = 0;
		int maxX = 0;
		for (String effectName : potionEffects) {
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(effectName, pos.getAbsoluteX(), pos.getAbsoluteY() + count * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, -1);
			if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(effectName) > maxX) maxX = Minecraft.getMinecraft().fontRendererObj.getStringWidth(effectName);
			count++;
		}
		this.width = maxX;
		this.height = count * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		ArrayList<String> potionEffects = getPotionEffects();
		if (potionEffects.size() <= 0) {
			this.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth("Potion Effect");
			this.height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Potion Effect", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
		} else {
			render(pos);
		}
	}

	@Override
	public Setting getSettings() {
		return settings;
	}

}
