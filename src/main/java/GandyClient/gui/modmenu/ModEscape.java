package GandyClient.gui.modmenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.lwjgl.input.Keyboard;

import GandyClient.Constants;
import GandyClient.gui.hud.HUDManager;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ModEscape extends GuiScreen {
	
	private final ModEscapeButton modMenuBtn = new ModEscapeButton("Mod Menu");
	private final ModEscapeButton hudConfigBtn = new ModEscapeButton("HUD Config");
	private final HashMap<ModEscapeButton, ScreenPosition> registeredButtons = new HashMap<ModEscapeButton, ScreenPosition>();
	private double defaultX = 0.5, defaultY = 0.5, bWidth = Constants.MOD_ESC_WIDTH, bHeight = Constants.MOD_ESC_HEIGHT; 
	
	public ModEscape () {
		registeredButtons.put( // put mod menu button
				modMenuBtn,
				ScreenPosition.fromRelativePosition(defaultX - (bWidth/2), defaultY - bHeight)
				);
		registeredButtons.put( // put hud config button
				hudConfigBtn, 
				ScreenPosition.fromRelativePosition(defaultX-(bWidth/2), defaultY + (bHeight/2))
				);
	}	
	
	@Override
	public void drawScreen (int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		final float zBackup = this.zLevel;
		this.zLevel = 200;
		
		for (ModEscapeButton button : registeredButtons.keySet()) {
			ScreenPosition pos = registeredButtons.get(button);
			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
			button.draw(this, pos.getAbsoluteX(), pos.getAbsoluteY(), (int) (bWidth * res.getScaledWidth()), (int) (bHeight * res.getScaledHeight()));
		}
		
		this.zLevel = zBackup;
	}	
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		// exits mod menu
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RSHIFT) {
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		loadMouseOver(x, y);
	}
	
	private void loadMouseOver(int x, int y) {
		Optional<ModEscapeButton> button = registeredButtons.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
		if (button.isPresent()) {
			if (button.get().getType().equalsIgnoreCase("Mod Menu")) {
				ModuleManager.getInstance().openModMenu();
			} else {
				HUDManager.getInstance().openConfigScreen();
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
