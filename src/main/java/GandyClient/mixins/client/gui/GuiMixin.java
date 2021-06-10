package GandyClient.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

@Mixin(Gui.class)
public abstract class GuiMixin {
	@Shadow
	protected void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {}
	@Shadow
	protected void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {}
	@Shadow
	protected static void drawRect(int left, int top, int right, int bottom, int color) {}
}
