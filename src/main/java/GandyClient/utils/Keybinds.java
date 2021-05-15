package GandyClient.utils;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;

public class Keybinds {
	public static final KeyBinding CLIENT_GUI_MOD_POS = new KeyBinding("Client Overlay", Keyboard.KEY_RSHIFT, "GandyClient");
    public static final KeyBinding CLIENT_PERSPECTIVE = new KeyBinding("Toggle Perspective", Keyboard.KEY_LMENU, "GandyClient");
}
