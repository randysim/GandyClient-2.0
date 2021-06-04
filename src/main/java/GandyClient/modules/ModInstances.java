package GandyClient.modules;

import GandyClient.gui.hud.HUDManager;
import GandyClient.modules.impl.ModFPS;
import GandyClient.modules.impl.ModFullBright;
import GandyClient.modules.impl.ModKeystrokes;
import GandyClient.modules.impl.ModOldAnimations;
import GandyClient.modules.impl.ModPerspective;
import GandyClient.modules.impl.ModPotionEffects;
import GandyClient.modules.impl.ModXYZ;
import GandyClient.modules.impl.autogg.ModAutoGG;
import GandyClient.modules.impl.togglesprintsneak.ModToggleSprintSneak;
import net.minecraft.client.Minecraft;

public class ModInstances {
	
private static ModFPS modFPS;
	
	private static ModKeystrokes modKeystrokes;
	
	private static ModToggleSprintSneak modToggleSprintSneak;
	
	private static ModPotionEffects modPotionEffects;
	
	private static ModXYZ modXYZ;
	
	private static ModFullBright modFullBright;
	
	private static ModAutoGG modAutoGG;
	
	private static ModPerspective modPerspective;
	
	private static ModOldAnimations modOldAnimations;
	
	public static void register (ModuleManager api) {		
		modKeystrokes = new ModKeystrokes();
		api.register(modKeystrokes);
		SettingsManager.getInstance().register(modKeystrokes.getSettings());
		
		modFPS = new ModFPS();
		api.register(modFPS);
		SettingsManager.getInstance().register(modFPS.getSettings());
		
		modToggleSprintSneak = new ModToggleSprintSneak();
		api.register(modToggleSprintSneak);
		SettingsManager.getInstance().register(modToggleSprintSneak.getSettings());
		
		modPotionEffects = new ModPotionEffects();
		api.register(modPotionEffects);
		SettingsManager.getInstance().register(modPotionEffects.getSettings());
		
		modXYZ = new ModXYZ();
		api.register(modXYZ);
		SettingsManager.getInstance().register(modXYZ.getSettings());
		
		modFullBright = new ModFullBright();
		api.register(modFullBright);
		if (Minecraft.getMinecraft().gameSettings.gammaSetting == 100f) {
			HUDManager.getInstance().register(modFullBright);
		}
		SettingsManager.getInstance().register(modFullBright.getSettings());
		
		modAutoGG = new ModAutoGG();
		api.register(modAutoGG);
		SettingsManager.getInstance().register(modAutoGG.getSettings());
		
		modPerspective = new ModPerspective();
		api.register(modPerspective);
		SettingsManager.getInstance().register(modPerspective.getSettings());
		
		modOldAnimations = new ModOldAnimations();
		api.register(modOldAnimations);
		SettingsManager.getInstance().register(modOldAnimations.getSettings());
	}
	
	public static ModToggleSprintSneak getModToggleSprintSneak () {
		return modToggleSprintSneak;
	}
	
	public static ModAutoGG getModAutoGG () {
		return modAutoGG;
	}
	
	public static ModPerspective getModPerspective () {
		return modPerspective;
	}
	
}
