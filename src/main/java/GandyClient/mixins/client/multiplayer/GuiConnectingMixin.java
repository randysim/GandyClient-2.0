package GandyClient.mixins.client.multiplayer;

import GandyClient.core.discord.DiscordIPC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.core.Client;
import net.minecraft.client.multiplayer.GuiConnecting;

@Mixin(GuiConnecting.class)
public abstract class GuiConnectingMixin {

	@Inject(method = "connect", at = @At("RETURN"))
	private void updateServerRP (final String ip, final int port, CallbackInfo info) {
		DiscordIPC.INSTANCE.update("Playing " + ip + (port != 25565 ? ":" + port : ""), "In Game");
	}

}
