package GandyClient.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;

@Mixin(PlayerControllerMP.class)
public abstract class PlayerControllerMPMixin {
	@Inject(method = "init", at=@At("HEAD"))
	private void setClientHandler (Minecraft mcIn, NetHandlerPlayClient handler, CallbackInfo info) {
		Client.getInstance().setClientHandler(handler);
	}
}
