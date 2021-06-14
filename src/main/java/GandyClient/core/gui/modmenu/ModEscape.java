package GandyClient.core.gui.modmenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.lwjgl.input.Keyboard;

import GandyClient.Constants;
import GandyClient.core.gui.SettingInstances;
import GandyClient.core.gui.hud.HUDManager;
import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.gui.modmenu.screens.SettingsScreen;
import GandyClient.core.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ModEscape extends GuiScreen {
	
	private final ModEscapeButton modMenuBtn = new ModEscapeButton("Mod Menu");
	private final ModEscapeButton hudConfigBtn = new ModEscapeButton("HUD Config");
	private final ModEscapeButton settingsBtn = new ModEscapeButton("Settings");
	private final HashMap<ModEscapeButton, ScreenPosition> registeredButtons = new HashMap<ModEscapeButton, ScreenPosition>();
	private double defaultX = 0.5, defaultY = 0.5, bWidth = Constants.MOD_ESC_WIDTH, bHeight = Constants.MOD_ESC_HEIGHT; 
	private boolean isSetting = false;
	
	public ModEscape () {
		registeredButtons.put( // put mod menu button
				modMenuBtn,
				ScreenPosition.fromRelativePosition(defaultX - (bWidth/2), defaultY - (bHeight * 2))
				);
		registeredButtons.put( // put hud config button
				hudConfigBtn, 
				ScreenPosition.fromRelativePosition(defaultX-(bWidth/2), defaultY - (bHeight/2))
				);
		registeredButtons.put(
				settingsBtn, 
				ScreenPosition.fromRelativePosition(defaultX-(bWidth/2), defaultY + bHeight)
				);
	}	
	
	public void setIsSetting (boolean flag) {
		this.isSetting = flag;
	}
	
	@Override
	public void drawScreen (int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		
		final float zBackup = this.zLevel;
		this.zLevel = 200;
		
		if (!this.isSetting) {
		
			for (ModEscapeButton button : registeredButtons.keySet()) {
				ScreenPosition pos = registeredButtons.get(button);
				ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
				button.draw(this, pos.getAbsoluteX(), pos.getAbsoluteY(), (int) (bWidth * res.getScaledWidth()), (int) (bHeight * res.getScaledHeight()));
			}
		} else {
			SettingsScreen mainSettings = SettingInstances.getInstance().getSetting("Settings");
			mainSettings.drawScreen(this);
		}
		
		this.zLevel = zBackup;
	}	
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		// exits settings page
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RSHIFT) {
			if (this.isSetting) 
				SettingInstances.getInstance().getSetting("Settings").onClose();
			
			SettingInstances.getInstance().register();
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	public void onGuiClosed() {
		// actions here
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		if (this.isSetting) {
			SettingInstances.getInstance().getSetting("Settings").onClick(x, y);;
		} else {
			loadMouseOver(x, y);
		}
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		if (this.isSetting) {
			SettingInstances.getInstance().getSetting("Settings").mouseClickMove(x, y);
		}
	}
	
	private void loadMouseOver(int x, int y) {
		Optional<ModEscapeButton> button = registeredButtons.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
		if (button.isPresent()) {
			if (button.get().getType().equalsIgnoreCase("Mod Menu")) {
				ModuleManager.getInstance().openModMenu();
			} else if (button.get().getType().equalsIgnoreCase("HUD Config")){
				HUDManager.getInstance().openConfigScreen();
			} else {
				this.isSetting = true;
			}
		}
	}
	
	private class MouseOverFinder implements Predicate<ModEscapeButton> {
		
		private int mouseX, mouseY;
		
		public MouseOverFinder (int x, int y) {
			this.mouseX = x;
			this.mouseY = y;
		}
		
		@Override
		public boolean test(ModEscapeButton button) {
			
			ScreenPosition pos = registeredButtons.get(button);
			
			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();
			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
			int width = (int) (bWidth * res.getScaledWidth());
			int height = (int) (bHeight * res.getScaledHeight());
			
			if (mouseX >= absoluteX && mouseX <= absoluteX + width && mouseY >= absoluteY && mouseY <= absoluteY + height) {
				return true;
			}
			
			return false;
		}
		
	}
}
