package GandyClient.mixins.client.settings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.settings.KeyBinding;

@Mixin(KeyBinding.class)
public interface IKeyBindingMixin {
	@Accessor void setPressed(boolean pressed);
}
