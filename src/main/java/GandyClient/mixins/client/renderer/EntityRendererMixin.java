package GandyClient.mixins.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.events.impl.RenderEvent;
import net.minecraft.client.renderer.EntityRenderer;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
	
	@Inject(method = "renderWorld", at = @At("RETURN"))
	private void onRenderReturn (CallbackInfo info) {
		new RenderEvent().call();
	}
}
