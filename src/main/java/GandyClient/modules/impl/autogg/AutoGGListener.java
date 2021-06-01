package GandyClient.modules.impl.autogg;

import java.util.ArrayList;
import java.util.Arrays;

import GandyClient.events.EventTarget;
import GandyClient.events.impl.ChatEvent;
import GandyClient.gui.hud.HUDManager;
import GandyClient.modules.ModInstances;
import GandyClient.utils.ChatColor;
import GandyClient.utils.Multithreading;
import net.minecraft.client.Minecraft;

public class AutoGGListener {
	// Code Modified from Hyperium lol srry
	private ArrayList<String> ggTriggers;
	
	public AutoGGListener () {
		String rawTriggers = "1st Killer -\n"
				+ "1st Place -\n"
				+ "Winner:\n"
				+ " - Damage Dealt -\n"
				+ "Winning Team -\n"
				+ "1st -\n"
				+ "Winners:\n"
				+ "Winner:\n"
				+ "Winning Team:\n"
				+ " won the game!\n"
				+ "Top Seeker:\n"
				+ "1st Place:\n"
				+ "Last team standing!\n"
				+ "Winner #1 (\n"
				+ "Top Survivors\n"
				+ "Winners -\n"
				+ "Sumo Duel -\n"
				+ "The Bridge Duel -\n"
				+ "UHC Duel -\n"
				+ "SkyWars Duel -\n"
				+ "MegaWalls Duel -\n"
				+ "OP Duel -\n"
				+ "Bow Duel -\n"
				+ "Classic Duel -\n"
				+ "NoDebuff Duel -\n"
				+ "Blitz Duel -\n"
				+ "Combo Duel -\n"
				+ "Bow Spleef Duel -\n"
				+ "UHC Deathmatch -";
		this.ggTriggers = new ArrayList<>(Arrays.asList(rawTriggers.split("\n")));
	}
	
	@EventTarget
	public void onChat (ChatEvent event) {
		if (!HUDManager.getInstance().has(ModInstances.getModAutoGG())) return;
		
		String unformattedMessage = ChatColor.stripColor(event.getChat().getUnformattedText());
		
		if (ggTriggers.stream().anyMatch(unformattedMessage::contains) && unformattedMessage.startsWith(" ")) {
            Multithreading.POOL.submit(() -> {
                try {
                	if (ModInstances.getModAutoGG().getSettings().getSettings().get("SAY_L") == 0) {
                		Minecraft.getMinecraft().thePlayer.sendChatMessage("/achat " + "gg");
                	} else {
                		Minecraft.getMinecraft().thePlayer.sendChatMessage("/achat " + "L");
                	}
                    Thread.sleep(2000L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
	}
}
