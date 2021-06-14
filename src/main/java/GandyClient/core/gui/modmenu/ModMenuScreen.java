package GandyClient.core.gui.modmenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.lwjgl.input.Keyboard;

import GandyClient.Constants;
import GandyClient.core.gui.SettingInstances;
import GandyClient.core.gui.hud.HUDManager;
import GandyClient.core.gui.hud.IRenderer;
import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.gui.modmenu.screens.ModMenu;
import GandyClient.core.gui.modmenu.screens.SettingsScreen;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ModMenuScreen extends GuiScreen {
	private final HashMap<ModDraggable, ScreenPosition> registeredMods = new HashMap<ModDraggable, ScreenPosition>();
	private ModuleManager api;
	public int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
	public double modW = Constants.MOD_WIDTH, modH = Constants.MOD_HEIGHT;
	private double modMenuWidth = 1.0;
	private double modMenuHeight = 1.0;
	private long startTime;
	private int totalTime = Constants.EASE_TIME;
	private boolean finishedDraw = false;
	private ModMenu modContainer;
	private boolean settings = false;
	private String settingName = "";
	
	public ModMenuScreen (ModuleManager api) {
		this.api = api;
		int count = 0;
		int maxLine = (int) (1/modW);
		
		// the gui was designed with large scale (3x) in mind, scale these down/up
		// small - 1x
		// normal - 2x
		// large - 3x
		// auto - 9x
		
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int spaceAround = maxLine + 1;
		float maxGap = (float)((1-(modW*maxLine)))/(float)spaceAround;
		for (ModDraggable mod : api.getRegisteredMods()) {
			int x = (int) (maxGap * modMenuWidth * res.getScaledWidth()) + (count % maxLine)*((int)(modW * modMenuWidth * res.getScaledWidth()) + (int) (maxGap * modMenuWidth * res.getScaledWidth()));
			int y = (int) ( (float) (count/maxLine) <= 1 ? (modH/2) * modMenuHeight * res.getScaledHeight() : (modH/2) * modMenuHeight * res.getScaledHeight()) + ((int)(count / maxLine) * ((int)(modH * modMenuHeight * res.getScaledHeight())+ (int) (modH * modMenuHeight * res.getScaledHeight())));
			registeredMods.put(mod, ScreenPosition.fromAbsolute(x, y));
			count++;
		}
		
		modContainer = new ModMenu(0, 0, modMenuWidth, modMenuHeight, this.registeredMods);
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public void drawScreen (int mouseX, int mouseY, float partialTicks) {
		final float zBackup = this.zLevel;
		this.zLevel = 200;
		
		if (!settings) {
			/* MOD MENU SELECTIONS */
			ScreenPosition center = ScreenPosition.fromRelativePosition(0.5, 0.5);
			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
			
			// Ease
			if (!finishedDraw) {
				
				int easeY = easeIn((int) (System.currentTimeMillis() - this.startTime), 0, res.getScaledHeight(), this.totalTime);
				modContainer.setY(res.getScaledHeight() - easeY);
				if (System.currentTimeMillis() - this.startTime >= this.totalTime) {
					finishedDraw = true;
					modContainer.setY(0);
				}
			}
			
			modContainer.setX(center.getAbsoluteX()-(int)((modContainer.getWidth()*res.getScaledWidth())/2));
			modContainer.drawScreen(this);
		} else {
			// settings logic
			SettingsScreen screen = SettingInstances.getInstance().getSetting(this.settingName);
			if (screen != null) screen.drawScreen(this);
		}
		
		/* ================================ */
		
		this.zLevel = zBackup;
	}
	
	public int easeIn (int elapsed, int start, int end, int total) {
		float percent = (float)elapsed/(float)total;
		return (int)((float)end*percent*percent+start);
	}
	
	public void setIsSetting (boolean flag) {
		if (flag == false) {
			SettingInstances.getInstance().getSetting(this.settingName).onClose();
		}
		this.settings = flag;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RSHIFT) {
			if (this.settings)
				SettingInstances.getInstance().getSetting(this.settingName).onClose();
			SettingInstances.getInstance().register();
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		if (this.settings) {
			SettingInstances.getInstance().getSetting(this.settingName).onClick(x, y);
		} else {
			loadMouseOver(x, y);
		}
	}

	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		if (this.settings) {
			SettingInstances.getInstance().getSetting(this.settingName).mouseClickMove(x, y);
		}
	}
	
	private class MouseOverFinder implements Predicate<IRenderer> {
		
		private int mouseX, mouseY;
		
		public MouseOverFinder (int x, int y) {
			this.mouseX = x;
			this.mouseY = y;
		}
		
		@Override
		public boolean test(IRenderer renderer) {
			
			ScreenPosition pos = registeredMods.get(renderer);
			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
			
			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();
			
			if (this.mouseX >= absoluteX && this.mouseX <= absoluteX + (modW * res.getScaledWidth()) && mouseY >= absoluteY && mouseY <= (absoluteY + (modH * res.getScaledHeight()) * 1.5)) {
				return true;
			}
			
			return false;
		}
		
	}
	@Override
	public void onGuiClosed() {
		// actions here
	}
	
	private void loadMouseOver(int x, int y) {
		Optional<ModDraggable> selected = registeredMods.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
		if (selected.isPresent()) {
			ModDraggable mod = selected.get();
			ScreenPosition pos = registeredMods.get(mod);
			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
			
			if (y <= (pos.getAbsoluteY() + (modH * res.getScaledHeight()))) {
				if (HUDManager.getInstance().has(mod)) {
					this.api.disable(mod);
				} else {
					this.api.enable(mod);
				}
			} else {
				// open settings for mod
				this.settingName = mod.getDisplayName();
				this.settings = true;
			}
		}
		
		// test for settings buttons as well
	}
	
	public String getSettingName () {
		return this.settingName;
	}
}