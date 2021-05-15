package GandyClient.modules;

import GandyClient.gui.hud.IRenderer;
import GandyClient.gui.hud.ScreenPosition;

public abstract class ModDraggable extends Mod implements IRenderer {
	
	ScreenPosition pos;
	
	public ModDraggable () {
		// load pos from file
	}
	
	@Override
	public void save(ScreenPosition pos) {
		// TODO Auto-generated method stub
		this.pos = pos;
		// save position to file
	}

	@Override
	public ScreenPosition load() {
		// TODO Auto-generated method stub
		return pos;
	}
}
