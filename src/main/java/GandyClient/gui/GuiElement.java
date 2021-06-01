package GandyClient.gui;

import GandyClient.gui.hud.ScreenPosition;
import GandyClient.gui.modmenu.ModMenuScreen;

public interface GuiElement {
	int getWidth();
	int getHeight();
	ScreenPosition load();
	void save(ScreenPosition pos);
	void render(ScreenPosition pos);
	
	void onClick(int relativeX, int relativeY);
	void onDrag(int newX, int newY); // all of these are relative
	void onClick(int relativeX, int relativeY, ModMenuScreen reference);
	void saveSettings();
}
