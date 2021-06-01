package GandyClient.modules.impl;

import java.util.ArrayList;

import GandyClient.Constants;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.PotionEffect;

public class ModPotionEffects extends ModDraggable {
	
	private String type = "Potion Effects";
	private boolean isUseGl = false;
	private Minecraft mc = Minecraft.getMinecraft();
	private int width, height;
	private Setting settings;
	
	private String effectDefault (String codeName) {
		String effectName;
		switch(codeName) {
		case "potion.moveSpeed": 
			effectName = "Speed";
			break;
		case "potion.moveSlowdown":
			effectName = "Slowness";
			break;
		case "potion.digSpeed":
			effectName = "Haste";
			break;
		case "potion.digSlowDown":
			effectName = "Miner Fatigue";
			break;
		case "potion.damageBoost":
			effectName = "Strength";
			break;
		case "potion.jump":
			effectName = "Jump Boost";
			break;
		case "potion.confusion":
			effectName = "Nausea";
			break;
		case "potion.regeneration":
			effectName = "Regeneration";
			break;
		case "potion.resistance":
			effectName = "Resistance";
			break;
		case "potion.fireResistance":
			effectName = "Fire Resistance";
			break;
		case "potion.waterBreathing":
			effectName = "Water Breathing";
			break;
		case "potion.invisibility":
			effectName = "Invisbility";
			break;
		case "potion.blindness":
			effectName = "Blindness";
			break;
		case "potion.nightVision":
			effectName = "Night Vision";
			break;
		case "potion.hunger":
			effectName = "Hunger";
			break;
		case "potion.weakness":
			effectName = "Weakness";
			break;
		case "potion.poison":
			effectName = "Poison";
			break;
		case "potion.wither":
			effectName = "Wither";
			break;
		case "potion.healthBoost":
			effectName = "Health Boost";
			break;
		case "potion.absorption":
			effectName = "Absorption";
			break;
		case "potion.saturation":
			effectName = "Saturation";
			break;
		case "potion.heal":
			effectName = "Healing";
			break;
		case "potion.damage":
			effectName = "Damage";
			break;
		default:
			effectName = codeName;
			break;
		
		}
		return effectName;
	}
	
	private ArrayList<String> getPotionEffects () {
		ArrayList<String> potionEffects = new ArrayList<String>();
		for (PotionEffect effect : mc.thePlayer.getActivePotionEffects()) {
			String effectName = effectDefault(effect.getEffectName());
			String amplifier = "";
			int durationTicks = effect.getDuration();
			int durationSeconds = (int) durationTicks / 20;
			int durationMinutes = (int) durationSeconds / 60;
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return height;
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
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		// TODO Auto-generated method stub
		
		ArrayList<String> potionEffects = getPotionEffects();
		int count = 0;
		int maxX = 0;
		for (String effectName : potionEffects) {
			font.drawStringWithShadow(effectName, pos.getAbsoluteX(), pos.getAbsoluteY() + count * font.FONT_HEIGHT, -1);
			if (font.getStringWidth(effectName) > maxX) maxX = font.getStringWidth(effectName);
			count++;
		}
		
		this.width = maxX;
		this.height = count*font.FONT_HEIGHT;
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		// TODO Auto-generated method stub
		ArrayList<String> potionEffects = getPotionEffects();
		if (potionEffects.size() <= 0) {
			this.width = font.getStringWidth("Potion Effect");
			this.height = font.FONT_HEIGHT;
			font.drawStringWithShadow("Potion Effect", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
		} else {
			render(pos);
		}
	}

	@Override
	public Setting getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}

}
