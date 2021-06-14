package GandyClient.mixins.client.renderer.entity.layers;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import GandyClient.core.modules.SettingsManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;

@Mixin(LayerArmorBase.class)
public abstract class LayerArmorBaseMixin {
	
	@Shadow
	@Final
	private static Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP;
	
	@Overwrite
	private ResourceLocation getArmorResource(ItemArmor p_177178_1_, boolean p_177178_2_, String p_177178_3_)
    {
		boolean flag = p_177178_1_.getArmorMaterial().getName().equalsIgnoreCase(ItemArmor.ArmorMaterial.LEATHER.getName()) && SettingsManager.getInstance().getSettingValue("ModInvisibleArmor", "ENABLED") == 1;
        String s = String.format((flag ? "" : "textures/models/armor/") + "%s_layer_%d%s.png", new Object[] {p_177178_1_.getArmorMaterial().getName(), Integer.valueOf(p_177178_2_ ? 2 : 1), p_177178_3_ == null ? "" : String.format("_%s", new Object[]{p_177178_3_})});
        
        if (SettingsManager.getInstance().getSettingValue("ModInvisibleArmor", "ENABLED") == 1 && !flag) {
        	// cases for non leather armor
        	if (SettingsManager.getInstance().getSettingValue("ModInvisibleArmor", "SHOW_" + p_177178_1_.getArmorMaterial().getName().toUpperCase()) == 0) {
        		s = "leather_layer_1_overlay.png";
        	}
        }
       
        ResourceLocation resourcelocation = (ResourceLocation)ARMOR_TEXTURE_RES_MAP.get(s);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            ARMOR_TEXTURE_RES_MAP.put(s, resourcelocation);
        }

        return resourcelocation;
    }
	
}
