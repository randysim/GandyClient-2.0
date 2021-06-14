package GandyClient.core.modules.impl.togglesprintsneak;

import GandyClient.events.EventTarget;
import GandyClient.events.impl.ClientTickEvent;
import net.minecraft.client.Minecraft;
import GandyClient.mixins.client.settings.IKeyBindingMixin;
import GandyClient.core.modules.ModInstances;

public class ToggleSprintListener {
	@EventTarget
	public void onTick (ClientTickEvent e) {
		if (ModInstances.getModToggleSprintSneak().getSettings().getSettings().get("ENABLED") == 1) {
			((IKeyBindingMixin) (Minecraft.getMinecraft().gameSettings.keyBindSprint)).setPressed(true);
		} else {
			((IKeyBindingMixin) (Minecraft.getMinecraft().gameSettings.keyBindSprint)).setPressed(false);
		}
	}
}
