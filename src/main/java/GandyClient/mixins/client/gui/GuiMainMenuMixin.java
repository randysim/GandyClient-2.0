package GandyClient.mixins.client.gui;

import java.awt.Color;

import GandyClient.core.discord.DiscordIPC;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.core.Client;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreenMixin {
	/** OpenGL graphics card warning. */
	@Shadow
    private String openGLWarning1;
	@Shadow
	private String openGLWarning2;
	@Shadow
	private int field_92022_t;
	@Shadow
	private int field_92021_u;
	@Shadow
	private int field_92020_v;
	@Shadow
    private int field_92019_w;
	@Shadow
	private int field_92024_r;
	
	@Inject(method = "initGui", at = @At("HEAD"))
	private void init (CallbackInfo info) {
		DiscordIPC.INSTANCE.update("Idle", "Main Menu");
	}
	
	@Overwrite
	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
		// make custom gandyclient buttons here
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_ + (int)(p_73969_2_ * 0.8), I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("menu.multiplayer", new Object[0])));
	}
	
	@Overwrite
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	/* MAIN MENU PNG */
    	ScaledResolution scaledRes = new ScaledResolution(this.mc);
    	this.mc.getTextureManager().bindTexture(new ResourceLocation("mainmenu.png"));
    	Gui.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
    	
    	// logo
    	int gandyStringWidth = this.fontRendererObj.getStringWidth("GandyClient");
    	
    	GL11.glPushMatrix();
    	double scaleFactor = 3.25;
    	GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        this.fontRendererObj.drawStringWithShadow("GandyClient", (int)(((0.5 * scaledRes.getScaledWidth())/scaleFactor) - gandyStringWidth/2), (int)((0.2 * scaledRes.getScaledHeight())/scaleFactor), -1);
        GL11.glPopMatrix();

        String s = "GandyClient 1.8";

        if (this.mc.isDemo())
        {
            s = s + " Demo";
        }

        this.drawString(this.fontRendererObj, s, 2, this.height - 10, -1);
        String s1 = "Copyright Mojang AB. Do not distribute!";
        this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, Color.DARK_GRAY.getRGB());

        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0)
        {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
