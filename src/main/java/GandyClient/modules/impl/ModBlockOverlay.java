package GandyClient.modules.impl;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import GandyClient.Constants;
import GandyClient.events.EventTarget;
import GandyClient.events.impl.DrawBlockOutlineEvent;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class ModBlockOverlay extends ModDraggable {
	private String type = "Block Overlay";
	private boolean isUseGl = false;
	private Setting settings;
	
	public ModBlockOverlay () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		if (!settings.getSettings().containsKey("OUTLINE_WIDTH")) {
			settings.updateSetting("OUTLINE_WIDTH", (int) (Constants.FLOAT_SCALE * 0.25));
		}
		
		if (!settings.getSettings().containsKey("COLOR_RED")) {
			settings.updateSetting("COLOR_RED", 0);
		}
		if (!settings.getSettings().containsKey("COLOR_BLUE")) {
			settings.updateSetting("COLOR_BLUE", 0);
		}
		if (!settings.getSettings().containsKey("COLOR_GREEN")) {
			settings.updateSetting("COLOR_GREEN", 0);
		}
	}
	
	@EventTarget
	public void drawOverlay (DrawBlockOutlineEvent event) {
		if (settings.getSettings().get("ENABLED") == 0) return;
		
		EntityPlayer player = event.getPlayer();
		MovingObjectPosition position = event.getPosition();
		float partialTicks = event.getPartialTicks();
		
		
		GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color((float)settings.getSettings().get("COLOR_RED")/(float)Constants.FLOAT_SCALE, (float)settings.getSettings().get("COLOR_GREEN")/(float) Constants.FLOAT_SCALE, (float)settings.getSettings().get("COLOR_BLUE")/(float) Constants.FLOAT_SCALE, 1.0F);
        GL11.glLineWidth(
        		((float)settings.getSettings().get("OUTLINE_WIDTH")/(float)Constants.FLOAT_SCALE) * 8F
        );
        GlStateManager.disableTexture2D();

        GlStateManager.depthMask(false);
        float f = 0.002F;
        BlockPos blockpos = position.getBlockPos();
        Block block = mc.theWorld.getBlockState(blockpos).getBlock();

        if (block.getMaterial() != Material.air && mc.theWorld.getWorldBorder().contains(blockpos))
        {
            block.setBlockBoundsBasedOnState(mc.theWorld, blockpos);
            double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
            double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
            double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
            drawSelectionBoundingBox(block.getSelectedBoundingBox(mc.theWorld, blockpos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2));
        }

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();

        GlStateManager.disableBlend();
	}
	
	/* FROM RenderGlobal.java Minecraft */
	public static void drawSelectionBoundingBox(AxisAlignedBB p_181561_0_)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION);
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
        tessellator.draw();
    }
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return type;
	}
	@Override 
	public String getSimpleName () {
		return this.getClass().getSimpleName();
	}

	@Override
	public Setting getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}
}
