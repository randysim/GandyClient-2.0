package GandyClient.core.modules;

public class Mod {
	private boolean isEnabled = false;

	public Mod () {
	}
	
	public void setEnabled (boolean flag) {
		// setEnabled in mod draggable child classes based on settings
		isEnabled = flag;
	}
	
	public boolean isEnabled () {
		return this.isEnabled;
	}
}
