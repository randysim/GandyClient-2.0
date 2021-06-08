package GandyClient.mixins.world;

import java.time.LocalTime;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import GandyClient.Constants;
import GandyClient.modules.SettingsManager;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

@Mixin(World.class)
public abstract class WorldMixin {
	@Shadow
	private WorldInfo worldInfo;
	
	@Overwrite
	public void setWorldTime (long time) {
		if (SettingsManager.getInstance().getSettingValue("ModTimeChanger", "ENABLED") == 1) {
			if (SettingsManager.getInstance().getSettingValue("ModTimeChanger", "REAL_TIME") == 1) {
				this.worldInfo.setWorldTime((long) (((float)(LocalTime.now().getHour() + LocalTime.now().getMinute())/60F) * 1000F));
			} else {
				this.worldInfo.setWorldTime((long) (((float)SettingsManager.getInstance().getSettingValue("ModTimeChanger", "TIME")/(float)Constants.FLOAT_SCALE) * 21000F));
			}
		} else {
			this.worldInfo.setWorldTime(time);
		}
	}
}
