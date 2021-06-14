package GandyClient.core.modules.impl;

import java.awt.Color;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import GandyClient.Constants;
import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;

public class ModKeystrokes extends ModDraggable {
	
	public static enum KeyStrokesMode {
		
		WASD(Key.W, Key.A, Key.S, Key.D),
		WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
		WASD_SPRINT(Key.W, Key.A, Key.S, Key.D, new Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 41, 58, 18)),
		WASD_SPRINT_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB, new Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 61, 58, 18))
		;
		
		private final Key[] keys;
		private int width = 0;
		private int height = 0;
		
		private KeyStrokesMode (Key... keysIn) {
			this.keys = keysIn;
			
			for (Key key : this.keys) {
				this.width = Math.max(this.width, key.getX() + key.getWidth());
				this.height = Math.max(this.height, key.getHeight() + key.getY());
			}
		}
		
		public int getHeight() {
			return height;
		}
		public int getWidth() {
			return width;
		}
		public Key[] getKeys() {
			return keys;
		}
		
	}
	
	private static class Key {
		
		private static final Key W = new Key("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 21, 1, 18, 18);
		private static final Key A = new Key("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 1, 21, 18, 18);
		private static final Key S = new Key("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 21, 21, 18, 18);
		private static final Key D = new Key("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 41, 21, 18, 18);
		
		private static final Key LMB = new Key("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 1, 41, 28, 18);
		private static final Key RMB = new Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 31, 41, 28, 18);
		
		private final String name;
		private final KeyBinding keyBind;
		private final int x;
		private final int y;
		private final int width;
		private final int height;
		
		public Key (String name, KeyBinding keyBind, int x, int y, int width, int height) {
			this.name = name;
			this.keyBind = keyBind;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public boolean isDown () {
			return keyBind.isKeyDown();
		}
		
		public String getName() {
			return name;
		}
		public int getHeight() {
			return height;
		}
		public int getWidth() {
			return width;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
	}
	
	private KeyStrokesMode mode = KeyStrokesMode.WASD_MOUSE;
	private boolean isUseGl = true;
	public String type = "KeyStrokes";
	private Setting settings;
	
	public ModKeystrokes () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		if (!settings.getSettings().containsKey("SIZE")) {
			settings.updateSetting("SIZE", Constants.FLOAT_SCALE);
		}
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return mode.getWidth();
	}

	@Override
	public int getHeight() {
		return mode.getHeight();
	}
	
	@Override
	public String getDisplayName() {
		return type;
	}
	@Override 
	public String getSimpleName () {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
		double scaleFactor = 1;
		
		switch (guiScale) {
		case 1:
			scaleFactor = 4;
			break;
		case 2:
			scaleFactor = 2;
			break;
		case 3:
			scaleFactor = 1.33;
			break;
		}
		GlStateManager.pushMatrix();

		if (settings.getSettings().containsKey("SIZE")) {
			float floatScale = (float) settings.getSettings().get("SIZE") / (float) Constants.FLOAT_SCALE;
			scaleFactor *= floatScale;
		}
		
		GlStateManager.scale(scaleFactor, scaleFactor, 1.0F);
		// divide everything by scaleFactor after scaling up scaleFactor so coords become correct
		for (Key key : mode.getKeys()) {
			int textWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(key.getName());
			int x = (int) (pos.getAbsoluteX() / scaleFactor + key.getX());
			int y = (int) (pos.getAbsoluteX() / scaleFactor) + key.getX();
			Gui.drawRect(x, y, x + key.getWidth(), y + key.getHeight(), key.isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 102).getRGB());
			Minecraft.getMinecraft().fontRendererObj.drawString(key.getName(), x + key.getWidth() / 2 - textWidth / 2, y + key.getHeight() / 2 - 4, key.isDown() ? Color.BLACK.getRGB() : Color.white.getRGB());
		}
		GL11.glPopMatrix();
	}

	@Override
	public Setting getSettings() {
		return settings;
	}

}
