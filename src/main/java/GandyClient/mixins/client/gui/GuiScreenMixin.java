package GandyClient.mixins.client.gui;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

@Mixin(GuiScreen.class)
public abstract class GuiScreenMixin extends GuiMixin {
	@Shadow
	protected List<GuiButton> buttonList = Lists.<GuiButton>newArrayList();
	@Shadow
	protected int width;
	@Shadow
	protected int height;
	@Shadow
	protected FontRenderer fontRendererObj;
	@Shadow
	protected void drawScreen(int mouseX, int mouseY, float partialTicks) {}
	@Shadow
	protected Minecraft mc;
	
}
