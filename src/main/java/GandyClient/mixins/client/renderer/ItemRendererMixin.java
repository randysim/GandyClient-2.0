package GandyClient.mixins.client.renderer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import GandyClient.modules.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
	@Shadow
	private float equippedProgress;
	@Shadow
    private float prevEquippedProgress;
	@Shadow
	private Minecraft mc;
	@Shadow
	private ItemStack itemToRender;
	@Shadow
	private void renderItemMap(AbstractClientPlayer clientPlayer, float p_178097_2_, float p_178097_3_, float p_178097_4_) {}
	@Shadow
	private void transformFirstPersonItem(float equipProgress, float swingProgress) {}
	@Shadow
	private void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {}
	
	private int[] field_178094_a = new int[EnumAction.values().length];
	
	@Final
	@Shadow
	private RenderManager renderManager;
	
	/* IMPORTANT METHODS RENAMED FROM ItemRenderer.java */
	private void rotateAroundXAndY(float angle, float p_178101_2_)
    {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(p_178101_2_, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
	private void setLightMapFromPlayer(AbstractClientPlayer clientPlayer)
    {
        int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + (double)clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);
        
        // removed optifine dynamic lights here
        // may or may not need a replacement
        
        float f = (float)(i & 65535);
        float f1 = (float)(i >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }
	 private void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks)
	 {
	        float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
	        float f1 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
	        GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
	        GlStateManager.rotate((entityplayerspIn.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
	 }
	 private void performDrinking(AbstractClientPlayer clientPlayer, float p_178104_2_)
	    {
	        float f = (float)clientPlayer.getItemInUseCount() - p_178104_2_ + 1.0F;
	        float f1 = f / (float)this.itemToRender.getMaxItemUseDuration();
	        float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float)Math.PI) * 0.1F);

	        if (f1 >= 0.8F)
	        {
	            f2 = 0.0F;
	        }

	        GlStateManager.translate(0.0F, f2, 0.0F);
	        float f3 = 1.0F - (float)Math.pow((double)f1, 27.0D);
	        GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
	        GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
	        GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
	    }
	 private void doBlockTransformations()
	    {
	        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
	        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
	        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
	        
	        if (
	        		SettingsManager.getSettingValue("ModOldAnimations", "ENABLED") == 1 &&
	        		SettingsManager.getSettingValue("ModOldAnimations", "BLOCK_HIT") == 1
	        ) {
	        	GlStateManager.translate(-0.5F, 0.2F, 0.0F);
	        }
	    }
	 private void doBowTransformations(float p_178098_1_, AbstractClientPlayer clientPlayer)
	    {
	        GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
	        GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
	        GlStateManager.translate(-0.9F, 0.2F, 0.0F);
	        float f = (float)this.itemToRender.getMaxItemUseDuration() - ((float)clientPlayer.getItemInUseCount() - p_178098_1_ + 1.0F);
	        float f1 = f / 20.0F;
	        f1 = (f1 * f1 + f1 * 2.0F) / 3.0F;

	        if (f1 > 1.0F)
	        {
	            f1 = 1.0F;
	        }

	        if (f1 > 0.1F)
	        {
	            float f2 = MathHelper.sin((f - 0.1F) * 1.3F);
	            float f3 = f1 - 0.1F;
	            float f4 = f2 * f3;
	            GlStateManager.translate(f4 * 0.0F, f4 * 0.01F, f4 * 0.0F);
	        }

	        GlStateManager.translate(f1 * 0.0F, f1 * 0.0F, f1 * 0.1F);
	        GlStateManager.scale(1.0F, 1.0F, 1.0F + f1 * 0.2F);
	    }
	 
	 private void doItemUsedTransformations(float p_178105_1_)
	    {
	        float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float)Math.PI);
	        float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float)Math.PI * 2.0F);
	        float f2 = -0.2F * MathHelper.sin(p_178105_1_ * (float)Math.PI);
	        GlStateManager.translate(f, f1, f2);
	    }
	 private void renderPlayerArm(AbstractClientPlayer clientPlayer, float p_178095_2_, float p_178095_3_)
	    {
	        float f = -0.3F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI);
	        float f1 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI * 2.0F);
	        float f2 = -0.4F * MathHelper.sin(p_178095_3_ * (float)Math.PI);
	        GlStateManager.translate(f, f1, f2);
	        GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
	        GlStateManager.translate(0.0F, p_178095_2_ * -0.6F, 0.0F);
	        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
	        float f3 = MathHelper.sin(p_178095_3_ * p_178095_3_ * (float)Math.PI);
	        float f4 = MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI);
	        GlStateManager.rotate(f4 * 70.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(f3 * -20.0F, 0.0F, 0.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
	        GlStateManager.translate(-1.0F, 3.6F, 3.5F);
	        GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
	        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
	        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.scale(1.0F, 1.0F, 1.0F);
	        GlStateManager.translate(5.6F, 0.0F, 0.0F);
	        Render render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
	        GlStateManager.disableCull();
	        RenderPlayer renderplayer = (RenderPlayer)render;
	        renderplayer.renderRightArm(this.mc.thePlayer);
	        GlStateManager.enableCull();
	    }


	
	/* ====================================== */
	@Overwrite
	public void renderItemInFirstPerson  (float partialTicks) {
		float f = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
        EntityPlayerSP entityplayersp = this.mc.thePlayer;
        float f1 = entityplayersp.getSwingProgress(partialTicks);
        float f2 = entityplayersp.prevRotationPitch + (entityplayersp.rotationPitch - entityplayersp.prevRotationPitch) * partialTicks;
        float f3 = entityplayersp.prevRotationYaw + (entityplayersp.rotationYaw - entityplayersp.prevRotationYaw) * partialTicks;
        rotateAroundXAndY(f2, f3);
        setLightMapFromPlayer(entityplayersp);
        rotateWithPlayerRotations(entityplayersp, partialTicks);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();

        if (this.itemToRender != null)
        {
            if (this.itemToRender.getItem() instanceof ItemMap)
            {
                this.renderItemMap(entityplayersp, f2, f, f1);
            }
            else if (entityplayersp.getItemInUseCount() > 0)
            {
                EnumAction enumaction = this.itemToRender.getItemUseAction();
                int enumNum = 0;
                
                if (enumaction.ordinal() == EnumAction.NONE.ordinal()) enumNum = 1;
                if (enumaction.ordinal() == EnumAction.EAT.ordinal()) enumNum = 2;
                if (enumaction.ordinal() == EnumAction.DRINK.ordinal()) enumNum = 3;
                if (enumaction.ordinal() == EnumAction.BLOCK.ordinal()) enumNum = 4;
                if (enumaction.ordinal() == EnumAction.BOW.ordinal()) enumNum = 5;
                
                switch (enumNum)
                {
                    case 1:
                        this.transformFirstPersonItem(f, 0.0F);
                        break;

                    case 2:
                    case 3:
                    	performDrinking(entityplayersp, partialTicks);
                        this.transformFirstPersonItem(f, 0.0F);
                        break;

                    case 4:
                    	if (
            	        		SettingsManager.getSettingValue("ModOldAnimations", "ENABLED") == 1 &&
            	        		SettingsManager.getSettingValue("ModOldAnimations", "BLOCK_HIT") == 1
            	        ) {
                    		this.transformFirstPersonItem(0.2F, f1);
                    	} else {
                    		this.transformFirstPersonItem(f, 0.0F);
                    	}
                        doBlockTransformations();
                        break;

                    case 5:
                        this.transformFirstPersonItem(f, 0.0F);
                        doBowTransformations(partialTicks, entityplayersp);
                }
            }
            else
            {
            	doItemUsedTransformations(f1);
                this.transformFirstPersonItem(f, f1);
            }

            this.renderItem(entityplayersp, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        }
        else if (!entityplayersp.isInvisible())
        {
        	renderPlayerArm(entityplayersp, f, f1);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
	}
}
