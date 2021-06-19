package GandyClient.core;

import GandyClient.DataManager;
import GandyClient.FileManager;
import GandyClient.core.cosmetics.CapeManager;
import GandyClient.core.discord.DiscordIPC;
import GandyClient.events.EventManager;
import GandyClient.events.EventTarget;
import GandyClient.events.impl.ClientTickEvent;
import GandyClient.core.gui.SplashProgress;
import GandyClient.core.gui.hud.HUDManager;
import GandyClient.core.gui.modmenu.ModEscape;
import GandyClient.core.modules.ModInstances;
import GandyClient.core.modules.ModuleManager;
import GandyClient.core.modules.impl.autogg.AutoGGListener;
import GandyClient.core.modules.impl.togglesprintsneak.ToggleSprintListener;
import GandyClient.core.utils.Keybinds;
import GandyClient.core.utils.Multithreading;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Client {

	public static final Client INSTANCE = new Client();
	public static final Logger LOGGER = (Logger) LogManager.getLogger();

	private HUDManager hudManager;
	private ModuleManager modManager;
	@Getter
	public DataManager dataManager;
	@Getter
	public CapeManager capeManager;
	
	private NetHandlerPlayClient handler;
	
	private AutoGGListener ggListener = new AutoGGListener();
	private ToggleSprintListener sprintListener = new ToggleSprintListener();
	
	public void init () {
		// beginning of this.startGame in Minecraft.java
		FileManager.init();
		SplashProgress.setProgress(1, "Intializing Discord RP");
		DiscordIPC.INSTANCE.init();
		EventManager.register(this);
		capeManager = CapeManager.getInstance();
		
		/* LISTENERS */
		EventManager.register(ggListener);
		EventManager.register(sprintListener);
		
		SplashProgress.setProgress(1, "Fetching Data");
		Multithreading.POOL.submit(() -> {
            try {
                dataManager = new DataManager();
        		dataManager.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
		});
	}
	
	public void start () {
		// end of this.startGame in Minecraft.java
		hudManager = HUDManager.getInstance();
		modManager = ModuleManager.getInstance();
		ModInstances.register(modManager);
	}
	
	public void shutdown () {

	}
	
	public void setClientHandler (NetHandlerPlayClient netHandler) {
		handler = netHandler;
	}
	
	public NetHandlerPlayClient getClientHandler () {
		return handler;
	}
	
	@EventTarget
	public void onTick (ClientTickEvent e) {
		// add CLIENT_GUI_MOD_POS with mixins
		if (Keybinds.CLIENT_GUI_MOD_POS.isPressed()) {
			Minecraft.getMinecraft().displayGuiScreen(new ModEscape());
		}
	}

	public static void info (Object msg, Object... params) {
		LOGGER.info(String.valueOf(msg), params);
	}
	public static void error (Object msg, Object... params) {
		LOGGER.error(String.valueOf(msg), params);
	}
}
