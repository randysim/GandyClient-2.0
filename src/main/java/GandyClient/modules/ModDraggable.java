package GandyClient.modules;

import java.io.File;

import GandyClient.FileManager;
import GandyClient.gui.hud.IRenderer;
import GandyClient.gui.hud.ScreenPosition;

public abstract class ModDraggable extends Mod implements IRenderer {
	
	ScreenPosition pos;
	
	public ModDraggable () {
		pos = loadPositionFromFile();
	}
	
	@Override
	public void save(ScreenPosition pos) {
		// TODO Auto-generated method stub
		this.pos = pos;
		savePositionToFile();
	}

	@Override
	public ScreenPosition load() {
		// TODO Auto-generated method stub
		return pos;
	}
	

	private ScreenPosition loadPositionFromFile() {
		// TODO Auto-generated method stub
		ScreenPosition loaded = FileManager.readFromJson(new File(getFolder(), "pos.json"), ScreenPosition.class);
		
		if (loaded == null) {
			loaded = ScreenPosition.fromRelativePosition(0.5, 0.5);
			this.pos = loaded;
			savePositionToFile();
		}
		
		return loaded;
	}
	
	private void savePositionToFile() {
		// TODO Auto-generated method stub
		FileManager.writeJsonToFile(new File(getFolder(), "pos.json"), pos);
	}
	
	private File getFolder () {
		File folder = new File(FileManager.getModsDirectory(), this.getClass().getSimpleName()); 
		folder.mkdirs();
		return folder;
	}
}
