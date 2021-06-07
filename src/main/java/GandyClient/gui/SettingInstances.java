package GandyClient.gui;

import java.util.Set;

import com.google.common.collect.Sets;

import GandyClient.gui.elements.GuiCheckBox;
import GandyClient.gui.elements.GuiSlider;
import GandyClient.gui.modmenu.screens.SettingsScreen;

public class SettingInstances {
	private Set<SettingsScreen> settings;
	
	private static SettingInstances instance = null;
	
	public static SettingInstances getInstance () {
		if (instance == null) instance = new SettingInstances();
		return instance;
	}
	
	public void register () {
		settings = Sets.newHashSet();
		SettingsScreen FPS = new SettingsScreen(0, 0, 1.0, 1.0, "FPS");
		FPS.addComponents(
				new GuiSlider("Size", "ModFPS", "SIZE", 0.01F, 2F),
				new GuiCheckBox("Background", "ModFPS", "BACKGROUND")
		);
		settings.add(FPS);
		
		SettingsScreen AutoGG = new SettingsScreen(0, 0, 1.0, 1.0, "AutoGG");
		AutoGG.addComponents(
				new GuiCheckBox("Auto L", "ModAutoGG", "SAY_L")
		);
		settings.add(AutoGG);
		
		SettingsScreen Perspective = new SettingsScreen(0, 0, 1.0, 1.0, "Perspective");
		Perspective.addComponents(
				new GuiCheckBox("Invert Y", "ModPerspective", "INVERT_Y")
		);
		settings.add(Perspective);
		
		SettingsScreen PotionEffects = new SettingsScreen(0, 0, 1.0, 1.0, "Potion Effects");
		PotionEffects.addComponents(
				new GuiSlider("Size", "ModPotionEffects", "SIZE", 0.01F, 2F)
		);
		settings.add(PotionEffects);
		
		SettingsScreen KeyStrokes = new SettingsScreen(0, 0, 1.0, 1.0, "KeyStrokes");
		KeyStrokes.addComponents(
				new GuiSlider("Size", "ModKeyStrokes", "SIZE", 0.01F, 2F)
		);
		settings.add(KeyStrokes);
		
		SettingsScreen ToggleSprint = new SettingsScreen(0, 0, 1.0, 1.0, "Toggle Sprint");
		ToggleSprint.addComponents(
				new GuiSlider("Size", "ModToggleSprintSneak", "SIZE", 0.01F, 2F)
		);
		settings.add(ToggleSprint);
		
		SettingsScreen OldAnimations = new SettingsScreen(0, 0, 1.0, 1.0, "1.7 Animations");
		OldAnimations.addComponents(
				new GuiCheckBox("Block Hit", "ModOldAnimations", "BLOCK_HIT")
		);
		settings.add(OldAnimations);
		
		SettingsScreen BlockOverlay = new SettingsScreen(0, 0, 1.0, 1.0, "Block Overlay");
		BlockOverlay.addComponents(
			new GuiSlider("Outline Width", "ModBlockOverlay", "OUTLINE_WIDTH", 0.01F, 1F)	
		);	
		settings.add(BlockOverlay);
	}
	
	public SettingsScreen getSetting (String settingsName) {
		if (settings == null) {
			// registering missing settings...
			register();
		}
		// problem here is that settings is = to null for some reason
		for (SettingsScreen setting : settings) {
			if (setting.getName().equalsIgnoreCase(settingsName)) {
				return setting;
			}
		}
		
		SettingsScreen placeholderSettings = new SettingsScreen(0, 0, 1.0, 1.0, settingsName);
		settings.add(placeholderSettings);
		
		return placeholderSettings;
	}
}
