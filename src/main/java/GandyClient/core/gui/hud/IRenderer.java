package GandyClient.core.gui.hud;

import GandyClient.core.modules.Setting;

public interface IRenderer {
	public void save(ScreenPosition pos);
	public ScreenPosition load();
	
	public int getHeight ();
	public int getWidth ();
	public String getDisplayName();
	public String getSimpleName();
	boolean isUseGl();
	Setting getSettings();
	
	void render (ScreenPosition pos);
	
	default void renderDummy (ScreenPosition pos) {
		render(pos);
	}
	
	public default boolean isEnabled () {
		return true;
	}
}
