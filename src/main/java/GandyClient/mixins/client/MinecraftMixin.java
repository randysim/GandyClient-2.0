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
import GandyClient.utils.ClientLogger;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	
	@Shadow private PlayerControllerMP playerController;
	@Shadow private int rightClickDelayTimer;
	@Shadow EntityPlayerSP thePlayer;
	@Shadow private MovingObjectPosition objectMouseOver;
	@Shadow private WorldClient theWorld;
	@Shadow private EntityRenderer entityRenderer;
	@Shadow private int leftClickCounter;
	
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
    
    @SuppressWarnings("incomplete-switch")
	@Inject(method = "rightClickMouse", at = @At("HEAD"), cancellable = true)
    private void rightClickMouse(CallbackInfo info)
    {
    	if (this.playerController.getIsHittingBlock() && this.objectMouseOver != null) {
    		ItemStack itemstack = this.thePlayer.inventory.getCurrentItem();
    		this.rightClickDelayTimer = 4;
    		switch (this.objectMouseOver.typeOfHit) {
    			case BLOCK:
    				BlockPos blockpos = this.objectMouseOver.getBlockPos();
    				if (
    						this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air &&
    						itemstack.getItem() instanceof ItemBlock
    				) {
    						Vec3 hitVec = this.objectMouseOver.hitVec;
    						EnumFacing side = this.objectMouseOver.sideHit;
    						BlockPos hitPos = blockpos;
    						float f = (float)(hitVec.xCoord - (double)hitPos.getX());
    			        	float f1 = (float)(hitVec.yCoord - (double)hitPos.getY());
    			        	float f2 = (float)(hitVec.zCoord - (double)hitPos.getZ());
    						Client.getInstance().getClientHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(hitPos, side.getIndex(), this.thePlayer.inventory.getCurrentItem(), f, f1, f2));
                            this.thePlayer.swingItem();
    				}
    				break;
    		}
    		
    		info.cancel();
    	}
    }
    
    @Inject(method = "sendClickBlockToController", at = @At("RETURN"), cancellable = true)
    private void clickMouse (boolean leftClick, CallbackInfo info) {
    	if (leftClick && this.thePlayer.isUsingItem()) {
    		if ( 
    				this.objectMouseOver != null && 
    				this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
    			)
            {
                BlockPos blockpos = this.objectMouseOver.getBlockPos();

                if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air)
                {
                	// swinging animation not working for some reason
                    this.thePlayer.swingItem();
                }
            }
    		
    		info.cancel();
    	}
    }
    
}
