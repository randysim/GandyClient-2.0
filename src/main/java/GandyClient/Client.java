package GandyClient;

import GandyClient.cosmetics.CapeManager;
import GandyClient.events.EventManager;
import GandyClient.events.EventTarget;
import GandyClient.events.impl.ClientTickEvent;
import GandyClient.gui.SplashProgress;
import GandyClient.gui.hud.HUDManager;
import GandyClient.gui.modmenu.ModEscape;
import GandyClient.modules.ModInstances;
import GandyClient.modules.ModuleManager;
import GandyClient.modules.impl.autogg.AutoGGListener;
import GandyClient.modules.impl.togglesprintsneak.ToggleSprintListener;
import GandyClient.utils.Keybinds;
import GandyClient.utils.Multithreading;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;

public class Client {
	private static final Client INSTANCE = new Client();
	public static final Client getInstance () {
		return INSTANCE;
	}
	
	private GandyClientRP RichPresence = new GandyClientRP();
	private HUDManager hudManager;
	private ModuleManager modManager;
	private DataManager dataManager;
	private CapeManager capeManager;
	
	private NetHandlerPlayClient handler;
	
	private AutoGGListener ggListener = new AutoGGListener();
	private ToggleSprintListener sprintListener = new ToggleSprintListener();
	
	public void init () {
		// beginning of this.startGame in Minecraft.java
		FileManager.init();
		SplashProgress.setProgress(1, "Intializing Discord RP");
		RichPresence.start();
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
		// client shutdown
		RichPresence.shutdown();
	}
	
	public GandyClientRP getRichPresence () {
		return RichPresence;
	}
	
	public DataManager getDataManager () {
		return dataManager;
	}
	
	public CapeManager getCapeManager () {
		return capeManager;
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
}
