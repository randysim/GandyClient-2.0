package GandyClient.mixins.client.gui;

import GandyClient.core.discord.DiscordIPC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.core.Client;
import net.minecraft.client.gui.GuiMultiplayer;

@Mixin(GuiMultiplayer.class)
public abstract class GuiMultiplayerMixin {

	@Deprecated
	@Inject(method = "initGui", at=@At("HEAD"))
	private void multiplayerListRP (CallbackInfo info) {
		/**
		 * I don't see a reason, why this should be shown in the rp. Makes 0 sense.
		 * @author Kaimson
		 */
		DiscordIPC.INSTANCE.update("Idle", "Server List");
	}
}
