package GandyClient.modules;

import GandyClient.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Mod {
	private boolean isEnabled = false;
	protected final Minecraft mc;
	protected final FontRenderer font;
	protected final Client client;
	
	public Mod () {
		this.mc = Minecraft.getMinecraft();
		this.font = this.mc.fontRendererObj;
		this.client = Client.getInstance();
	}
	
	public void setEnabled (boolean flag) {
		// setEnabled in mod draggable child classes based on settings
		isEnabled = flag;
	}
	
	public boolean isEnabled () {
		return this.isEnabled;
	}
}
