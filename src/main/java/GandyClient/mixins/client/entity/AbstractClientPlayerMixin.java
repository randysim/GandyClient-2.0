package GandyClient.mixins.client.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.utils.CapeUtils;
import net.minecraft.client.entity.AbstractClientPlayer;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {
	
	
	@Inject(method = "<init>", at = @At("RETURN"))
	public void loadEntityCape (CallbackInfo ci) {
		/* 
		 IDE is heckin stupid and thinks this is referring to mixin class
		 so tried converting to object and then to abstractclientplayer to get the reference
		 and i think it works?
		 */
		CapeUtils.downloadCape((AbstractClientPlayer) (Object) this, 0); 
	}
}
