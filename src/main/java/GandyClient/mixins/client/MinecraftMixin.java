package GandyClient.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.Client;
import GandyClient.events.impl.ClientTickEvent;
import GandyClient.gui.SplashProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;

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

    @Inject(method = "startGame", at = @At(value = "INVOKE", remap = false, target = "java/util/List.add(Ljava/lang/Object;)Z", shift = At.Shift.BEFORE))
    private void loadResourcePack(CallbackInfo info) {
    	SplashProgress.setProgress(2, "Loading Default Resource");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "net/minecraft/client/Minecraft.createDisplay()V", shift = At.Shift.BEFORE))
    private void createDisplay(CallbackInfo info) {
    	SplashProgress.setProgress(3, "Creating Display");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/OpenGlHelper.initializeTextures()V", shift = At.Shift.BEFORE))
    private void textureLoaded(CallbackInfo info) {
    	SplashProgress.setProgress(4, "Loading Textures");
    }
    
    // the name of the class intitiate method is <init> so Class.<init>()V (V = void) would be how you call it.
    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "net/minecraft/client/resources/model/ModelManager.<init>(Lnet/minecraft/client/renderer/texture/TextureMap;)V", shift = At.Shift.BEFORE))
    private void loadModels (CallbackInfo info) {
    	SplashProgress.setProgress(5, "Loading Models");
    }
}
