package GandyClient.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.events.impl.ChatEvent;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;

@Mixin(GuiNewChat.class)
public abstract class GuiNewChatMixin {
	// mixins can actual accept arguments this is amazing
	// but you have to copy all of the method's arguments
	// exactly before the callback info arg or else it won't work
	@Inject(method = "setChatLine", at = @At("HEAD"))
	private void setChatLineHead (
			IChatComponent p_146237_1_, 
			int p_146237_2_, 
			int p_146237_3_, 
			boolean p_146237_4_,
			CallbackInfo ci
			) {
		
		new ChatEvent(p_146237_1_).call();
	}
}
