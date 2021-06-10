package GandyClient.mixins.client.gui;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

@Mixin(GuiButton.class)
public abstract class GuiButtonMixin extends GuiMixin {
	@Shadow
	private boolean visible;
	@Shadow
	private boolean hovered;
	@Shadow
	private int getHoverState(boolean mouseOver) {
		return 0;
	}
	@Shadow private int xPosition;
	@Shadow private int yPosition;
	@Shadow private void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
	@Shadow private boolean enabled;
	@Shadow private int width;
	@Shadow private int height;
	@Shadow private String displayString;
	
	
	@Overwrite
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
        	// edit how buttons look here
            FontRenderer fontrenderer = mc.fontRendererObj;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;
            
            Color buttonColor = new Color(0, 0, 0, 102);
            
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
                buttonColor = new Color(0, 0, 0, 150);
            }
            
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition+this.width, this.yPosition+this.height, buttonColor.getRGB());
            super.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }
}
