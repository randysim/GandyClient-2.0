package GandyClient.mixins.client;

import GandyClient.core.Client;
import GandyClient.core.discord.DiscordIPC;
import GandyClient.core.gui.SplashProgress;
import GandyClient.events.impl.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	
	@Shadow
	public PlayerControllerMP playerController;
	@Shadow
	private int rightClickDelayTimer;
	@Shadow
	public EntityPlayerSP thePlayer;
	@Shadow
	public MovingObjectPosition objectMouseOver;
	@Shadow
	public WorldClient theWorld;
	@Shadow
	public EntityRenderer entityRenderer;
	@Shadow
	private int leftClickCounter;
	
	@Inject(method = "startGame", at = @At("HEAD"))
    private void init(CallbackInfo info) {
        Client.INSTANCE.init();
    }
	
	@Inject(method = "startGame", at = @At("RETURN"))
	private void start (CallbackInfo info) {
		Client.INSTANCE.start();
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
		Client.info("Loading Textures");
    	SplashProgress.setProgress(2, "Loading Textures");
    }
    
    // the name of the class intitiate method is <init> so Class.<init>()V (V = void) would be how you call it.
    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "net/minecraft/client/resources/model/ModelManager.<init>(Lnet/minecraft/client/renderer/texture/TextureMap;)V", shift = At.Shift.BEFORE))
    private void loadModels (CallbackInfo info) {
    	Client.info("Loading Models");
    	SplashProgress.setProgress(3, "Loading Models");
    }
    
    @Inject(method = "launchIntegratedServer", at = @At("RETURN"))
    private void singlePlayerRP (String folderName, String worldName, WorldSettings worldSettingsIn, CallbackInfo info) {
    	DiscordIPC.INSTANCE.update("Singleplayer World", "In Game");
    }
    
}
