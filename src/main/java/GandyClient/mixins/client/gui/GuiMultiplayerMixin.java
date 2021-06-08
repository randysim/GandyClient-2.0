package GandyClient.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.Client;
import net.minecraft.client.gui.GuiMultiplayer;

@Mixin(GuiMultiplayer.class)
public abstract class GuiMultiplayerMixin {
	@Inject(method = "initGui", at=@At("HEAD"))
	private void multiplayerListRP (CallbackInfo info) {
		Client.getInstance().getRichPresence().update("Idle", "Server List");
	}
}
