package GandyClient.core.gui;

import java.util.HashMap;

import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenContainer {
	private int cX;
	private int cY;
	private double cWidth;
	private double cHeight;
	private HashMap<ModDraggable, ScreenPosition> components;
	
	public ScreenContainer (int cX, int cY, double cWidth, double cHeight) {
		this.cX = cX;
		this.cY = cY;
		this.cWidth = cWidth;
		this.cHeight = cHeight;
	}
	
	/* GETTERS AND SETTERS */
	public int getX () {
		return cX;
	}
	public void setX (int x) {
		this.cX = x;
	}
	public int getY () {
		return cY;
	}
	public void setY (int y) {
		this.cY = y;
	}
	public double getWidth () {
		return cWidth;
	}
	public int getScaledWidth () {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		return (int) (cWidth * res.getScaledWidth());
	}
	public void setWidth (double w) {
		this.cWidth = w;
	}
	public double getHeight () {
		return this.cHeight;
	}
	public int getScaledHeight () {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		return (int) (this.cHeight * res.getScaledHeight());
	}
	public void setHeight (double h) {
		this.cHeight = h;
	}
	public void setComponents (HashMap<ModDraggable, ScreenPosition> components) {
		this.components = components;
	}
	
	public HashMap<ModDraggable, ScreenPosition> getComponents () {
		return this.components;
	}
}