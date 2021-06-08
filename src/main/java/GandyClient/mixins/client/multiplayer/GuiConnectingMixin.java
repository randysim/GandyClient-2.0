package GandyClient.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.Client;
import net.minecraft.client.multiplayer.GuiConnecting;

@Mixin(GuiConnecting.class)
public abstract class GuiConnectingMixin {
	@Inject(method = "connect", at = @At("RETURN"))
	private void updateServerRP (final String ip, final int port, CallbackInfo info) {
		Client.getInstance().getRichPresence().update("Playing " + ip + (port != 25565 ? ":" + port : ""), "In Game");
	}
}
