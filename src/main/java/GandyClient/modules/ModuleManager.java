package GandyClient.modules;

import java.util.Set;

import com.google.common.collect.Sets;

import GandyClient.events.EventManager;
import GandyClient.gui.hud.HUDManager;
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
		if (!registeredMods.contains(mod)) registeredMods.add(mod);
	}
	
	public Set<ModDraggable> getRegisteredMods () {
		return registeredMods;
	}
	
	public void enable (ModDraggable mod) {
		if (mod.isEnabled()) return;
		mod.setEnabled(true);
		// add to hud manager
		HUDManager.getInstance().register(mod);
		// update settings
	}
	public void disable (ModDraggable mod) {
		if (!mod.isEnabled()) return;
		mod.setEnabled(false);
		// remove from hud manager
		HUDManager.getInstance().unregister(mod);
		// update settings
	}
}
