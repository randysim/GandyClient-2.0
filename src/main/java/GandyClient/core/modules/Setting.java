package GandyClient.core.modules;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import GandyClient.FileManager;

public class Setting {
	private HashMap<String, Integer> config = new HashMap<String, Integer>();
	private String className;
	
	public Setting (String className) {
		this.className = className;
		config = loadSettingsFromFile();
	}
	
	public void updateSetting(String key, int value) {
		config.put(key, value);
		saveSettingToFile();
	}
	
	public String getModName () {
		return className;
	}
	
	public HashMap<String, Integer> getSettings () {
		return config;
	}

	private HashMap<String, Integer> loadSettingsFromFile() {
		// TODO Auto-generated method stub
		Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
		HashMap<String, Integer> settings = FileManager.readFromJson(new File(getFolder(), "config.json"), type);
		if (settings == null) {
			saveSettingToFile();
			return new HashMap<String, Integer>();
		}
		
		return settings;
	}
	
	private void saveSettingToFile() {
		// TODO Auto-generated method stub
		FileManager.writeJsonToFile(new File(getFolder(), "config.json"), config);
	}
	
	private File getFolder () {
		File folder = new File(FileManager.getModsDirectory(), this.className); 
		folder.mkdirs();
		return folder;
	}
}
