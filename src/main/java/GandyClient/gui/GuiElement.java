package GandyClient.gui;

import GandyClient.gui.hud.ScreenPosition;
import GandyClient.gui.modmenu.ModMenuScreen;

public interface GuiElement {
	int getWidth();
	int getHeight();
	ScreenPosition load();
	void save(ScreenPosition pos);
	void render(ScreenPosition pos);
	
	void onClick(int relativeX, int relativeY); // these are relative for some reason lmao
	void onDrag(int newX, int newY); 
	void saveSettings();
}
