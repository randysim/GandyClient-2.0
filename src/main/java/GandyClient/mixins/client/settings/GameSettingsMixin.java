package GandyClient.mixins.client.settings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.utils.Keybinds;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

@Mixin(GameSettings.class)
public abstract class GameSettingsMixin {
	
	@Shadow
	public KeyBinding[] keyBindings;
	
	// injected into return at constructor
	@Inject(method="<init>", at = @At("RETURN"))
	private void addClientKeybinds (CallbackInfo info) {
		// no array utils rip i am too lazy to add it.
		KeyBinding[] buffer = new KeyBinding[this.keyBindings.length + 2];
		for (int n = 0; n < this.keyBindings.length; ++n) {
			buffer[n] = this.keyBindings[n]; // copy all keys
		}
		
		buffer[this.keyBindings.length] = Keybinds.CLIENT_GUI_MOD_POS;
		buffer[this.keyBindings.length+1] = Keybinds.CLIENT_PERSPECTIVE;
		this.keyBindings = buffer;
	}
}
