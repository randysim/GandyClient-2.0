package GandyClient.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.core.modules.SettingsManager;
import GandyClient.core.modules.impl.itemphysics.ClientPhysic;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;

@Mixin(RenderEntityItem.class)
public abstract class RenderEntityItemMixin {
	@Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
	private void renderItemPhysic (EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
		if (SettingsManager.getInstance().getSettingValue("ModItemPhysics", "ENABLED") == 1) {
			ClientPhysic.doRender(entity, x, y, z);
			info.cancel(); // exit out of the function
		}
	}
}
