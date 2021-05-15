package GandyClient.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.Client;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Inject(method = "startGame", at = @At("HEAD"))
    private void init(CallbackInfo info) {

        Client.getInstance().init();
    }
	
	@Inject(method = "startGame", at = @At("RETURN"))
	private void start (CallbackInfo info) {
		Client.getInstance().start();
	}
}
