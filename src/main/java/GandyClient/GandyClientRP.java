package GandyClient;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class GandyClientRP {
	public boolean running = true;
	private long created = 0;
	
	public void start () {
		this.created = System.currentTimeMillis();
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {

			@Override
			public void apply(DiscordUser user) {
				System.out.println("Authenticated " + user.username + user.discriminator + ".");
				update("Loading...", "");
			}
			
		}).build();
		
		DiscordRPC.discordInitialize("835268116057555016", handlers, true);
		
		new Thread("DiscordRPC Callback") {
			@Override
			public void run () {
				while (running) {
					DiscordRPC.discordRunCallbacks();
				}
			}
		}.start();
	}
	
	public void shutdown () {
		this.running = false;
		DiscordRPC.discordShutdown();
	}
	
	public void update (String firstLine, String secondLine) {
		DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
		b.setBigImage("background", "");
		b.setDetails(firstLine);
		b.setStartTimestamps(this.created);
		DiscordRPC.discordUpdatePresence(b.build());
	}
}
