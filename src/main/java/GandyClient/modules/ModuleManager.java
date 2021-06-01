package GandyClient.modules;

import java.util.Set;

import com.google.common.collect.Sets;

import GandyClient.events.EventManager;
import GandyClient.gui.hud.HUDManager;
import GandyClient.gui.modmenu.ModMenuScreen;
import net.minecraft.client.Minecraft;

public class ModuleManager {
	private ModuleManager () {}
	
	private static ModuleManager INSTANCE = null;
	public static ModuleManager getInstance () {
		if (INSTANCE == null) {
			INSTANCE = new ModuleManager();
			EventManager.register(INSTANCE);
		}
		return INSTANCE;
	}
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	private Set<ModDraggable> registeredMods = Sets.newHashSet();
	
	public void register (ModDraggable mod) {
			this.registeredMods.add(mod);
			if (mod.getSettings().getSettings().get("ENABLED") == 1) {
				this.enable(mod);
			}
	}
	
	public Set<ModDraggable> getRegisteredMods () {
		return registeredMods;
	}
	
	public void enable (ModDraggable mod) {
		if (this.registeredMods.contains(mod)) HUDManager.getInstance().register(mod);
		mod.getSettings().updateSetting("ENABLED", 1);
	}
	public void disable (ModDraggable mod) {
		if (this.registeredMods.contains(mod)) {
			HUDManager.getInstance().unregister(mod);
			
			mod.getSettings().updateSetting("ENABLED", 0);
			
			// fullbright logic
			if (mod.getDisplayName().equalsIgnoreCase("Fullbright")) {
				mc.gameSettings.gammaSetting = 0f;
			}
		};
	}
	
	public void openModMenu () {
		mc.displayGuiScreen(new ModMenuScreen(this));
	}
}
