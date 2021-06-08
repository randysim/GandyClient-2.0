package GandyClient.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.Client;
import GandyClient.events.impl.ClientTickEvent;
import GandyClient.gui.SplashProgress;
import GandyClient.utils.ClientLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.WorldSettings;

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
	
	@Inject(method = "runTick", at = @At("RETURN"))
	private void runTick (CallbackInfo info) {
		new ClientTickEvent().call();
	}
	
	@Overwrite
	private void drawSplashScreen (TextureManager renderEngine) {
	
		SplashProgress.drawSplash(renderEngine);
	}

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/OpenGlHelper.initializeTextures()V", shift = At.Shift.BEFORE))
    private void textureLoaded(CallbackInfo info) {
    	ClientLogger.info("Loading Textures");
    	SplashProgress.setProgress(2, "Loading Textures");
    }
    
    // the name of the class intitiate method is <init> so Class.<init>()V (V = void) would be how you call it.
    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "net/minecraft/client/resources/model/ModelManager.<init>(Lnet/minecraft/client/renderer/texture/TextureMap;)V", shift = At.Shift.BEFORE))
    private void loadModels (CallbackInfo info) {
    	ClientLogger.info("Loading Models");
    	SplashProgress.setProgress(3, "Loading Models");
    }
    
    @Inject(method = "launchIntegratedServer", at = @At("RETURN"))
    private void singlePlayerRP (String folderName, String worldName, WorldSettings worldSettingsIn, CallbackInfo info) {
    	Client.getInstance().getRichPresence().update("Singleplayer World", "In Game");
    }
    
}
