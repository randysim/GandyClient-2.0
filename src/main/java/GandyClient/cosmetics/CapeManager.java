package GandyClient.cosmetics;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CapeManager {
	
	private static CapeManager INSTANCE = null;
	
	public static CapeManager getInstance () {
		if (INSTANCE == null) INSTANCE = new CapeManager();
		return INSTANCE;
	}
	
	private HashMap<String, ResourceLocation> capeMap = new HashMap<String, ResourceLocation>();
	
	public CapeManager () {}
	
	public void addCape (String playerName, ResourceLocation capeLocation) {
		this.capeMap.put(playerName.toLowerCase(), capeLocation);
	}
	
	public void addColoredCape (String playerName, int color, ResourceLocation capeLocation) {
 		this.capeMap.put(playerName.toLowerCase() + "_" + Integer.toString(color), capeLocation);
	}
	
	public ResourceLocation getCape (String playerName) {
		return capeMap.get(playerName.toLowerCase());
	}
	
	public ResourceLocation getColoredCape (String playerName, int color) {
		return capeMap.get(playerName.toLowerCase() + "_" + Integer.toString(color));
	}
}
