package GandyClient.gui;

import java.util.Set;

import com.google.common.collect.Sets;

import GandyClient.gui.elements.GuiCheckBox;
import GandyClient.gui.elements.GuiColorPicker;
import GandyClient.gui.elements.GuiLabel;
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
			new GuiSlider("Outline Width", "ModBlockOverlay", "OUTLINE_WIDTH", 0.01F, 2F),
			new GuiColorPicker("ModBlockOverlay")
		);	
		settings.add(BlockOverlay);
		
		SettingsScreen TimeChanger = new SettingsScreen(0, 0, 1.0, 1.0, "Time Changer");
		TimeChanger.addComponents( 
				new GuiSlider("Time", "ModTimeChanger", "TIME", 0.01F, 1F),
				new GuiCheckBox("Use Real Time", "ModTimeChanger", "REAL_TIME")
		);
		settings.add(TimeChanger);
		
		SettingsScreen ItemPhysics =  new SettingsScreen(0, 0, 1.0, 1.0, "Item Physics");
		ItemPhysics.addComponents(
				new GuiSlider("Rotation Speed", "ModItemPhysics", "ROTATION_SPEED", 0.01F, 1F)
		);
		settings.add(ItemPhysics);
		
		SettingsScreen InvisibleArmor = new SettingsScreen(0, 0, 1.0, 1.0, "Invisible Armor");
		InvisibleArmor.addComponents(
				new GuiLabel("Headband Credit: oChubby"),
				new GuiCheckBox("Show Headband", "ModInvisibleArmor", "HEADBAND"),
				new GuiCheckBox("Show Iron Armor", "ModInvisibleArmor", "SHOW_IRON"),
				new GuiCheckBox("Show Gold Armor", "ModInvisibleArmor", "SHOW_GOLD"),
				new GuiCheckBox("Show Diamond Armor", "ModInvisibleArmor", "SHOW_DIAMOND")
		);
		settings.add(InvisibleArmor);
		
		SettingsScreen CapeModifier = new SettingsScreen(0, 0, 1.0, 1.0, "Cape Modifier");
		CapeModifier.addComponents(
				new GuiCheckBox("Team Color Cape ( Bedwars Only )", "ModCapeModifier", "TEAM_COLOR_CAPE")
		);
		settings.add(CapeModifier);
		
		SettingsScreen MainSettings = new SettingsScreen(0, 0, 1.0, 1.0, "Settings");
		MainSettings.addComponents(
				new GuiColorPicker("ModSettings")
		);
		settings.add(MainSettings);
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
