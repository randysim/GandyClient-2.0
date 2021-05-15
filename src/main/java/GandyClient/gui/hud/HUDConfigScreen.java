package GandyClient.gui.hud;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.lwjgl.input.Keyboard;

import GandyClient.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class HUDConfigScreen extends GuiScreen {
	private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<IRenderer, ScreenPosition>();
	
	private Optional<IRenderer> selectedRenderer = Optional.empty();
	
	private int prevX, prevY;
	
	public HUDConfigScreen (HUDManager api) {
		Collection<IRenderer> registeredRenderers = api.getRegisteredRenderers();
		
		for (IRenderer render : registeredRenderers) {
			if (!render.isEnabled()) continue;
			
			ScreenPosition pos = render.load();
			
			if (pos == null) {
					pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
			}
			
			adjustBounds(render, pos);
			this.renderers.put(render, pos);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		
		final float zBackup = this.zLevel;
		this.zLevel = 200;
		
		this.drawHollowRect(0, 0, this.width - 1, this.height - 1, 0xFFFF0000);
		
		for (IRenderer renderer : renderers.keySet()) {
			ScreenPosition pos = renderers.get(renderer);
			int guiScale = mc.gameSettings.guiScale;
			double scaleFactor = 1;
			
			switch (guiScale) {
			case 1:
				scaleFactor = 4;
				break;
			case 2:
				scaleFactor = 2;
				break;
			case 3:
				scaleFactor = 1.33;
				break;
			}
			HashMap<String, Integer> settings = renderer.getSettings().getSettings();
			float floatScale;
			
			if (settings.containsKey("SIZE")) {
				floatScale = (float)settings.get("SIZE")/(float)Constants.FLOAT_SCALE;
				scaleFactor *= floatScale;
			}
			
			ScreenPosition bufferPos = ScreenPosition.fromRelativePosition(pos.getRelativeX(), pos.getRelativeY());
			bufferPos.setAbsolute((int)(bufferPos.getAbsoluteX()/scaleFactor), (int)(bufferPos.getAbsoluteY()/scaleFactor));
			
			if (!renderer.isUseGl()) {
				GlStateManager.pushMatrix();
				GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);
				this.drawHollowRect(bufferPos.getAbsoluteX(), bufferPos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), 0xFF00FFFF);
				renderer.renderDummy(bufferPos);
				GlStateManager.popMatrix();
			} else {
				this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), (int) (renderer.getWidth()*scaleFactor), (int) (renderer.getHeight()*scaleFactor), 0xFF00FFFF);
				renderer.renderDummy(pos);
			}
		}
		
		this.zLevel = zBackup;
	}

	private void drawHollowRect(int x, int y, int w, int h, int color) {
		this.drawHorizontalLine(x, x+w, y, color);
		this.drawHorizontalLine(x, x+w, y+h, color);
		this.drawVerticalLine(x, y+h, y, color);
		this.drawVerticalLine(x+w, y+h, y, color);
		
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RSHIFT) {
			renderers.entrySet().forEach((entry) -> {
				entry.getKey().save(entry.getValue());
			});
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		if (selectedRenderer.isPresent()) {
			moveSelectedRendererBy(x - prevX, y - prevY);
		}
		
		this.prevX = x;
		this.prevY = y;
	}

	private void moveSelectedRendererBy(int offsetX, int offsetY) {
		IRenderer renderer = selectedRenderer.get();
		ScreenPosition pos = renderers.get(renderer);
		
		pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);
		
		adjustBounds(renderer, pos);
	}
	
	@Override
	public void onGuiClosed() {
		
		for (IRenderer renderer: renderers.keySet()) {
			renderer.save(renderers.get(renderer));
		}
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	private void adjustBounds (IRenderer renderer, ScreenPosition pos) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int screenWidth = res.getScaledWidth();
		int screenHeight = res.getScaledHeight();
		
		int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
		double scaleFactor = 1;
		
		switch (guiScale) {
		case 1:
			scaleFactor = 4;
			break;
		case 2:
			scaleFactor = 2;
			break;
		case 3:
			scaleFactor = 1.33;
			break;
		}
		
		HashMap<String, Integer> settings = renderer.getSettings().getSettings();
		float floatScale;
		
		if (settings.containsKey("SIZE")) {
			floatScale = (float)settings.get("SIZE")/(float)Constants.FLOAT_SCALE;
			scaleFactor *= floatScale;
		}
		
		int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - (int) (renderer.getWidth()*scaleFactor), 0)));
		int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - (int) (renderer.getHeight()*scaleFactor), 0)));
		
		pos.setAbsolute(absoluteX, absoluteY);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		this.prevX = x;
		this.prevY = y;
		
		loadMouseOver(x, y);
	}

	private void loadMouseOver(int x, int y) {
		this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
	}
	
	private class MouseOverFinder implements Predicate<IRenderer> {
		
		private int mouseX, mouseY;
		
		public MouseOverFinder (int x, int y) {
			this.mouseX = x;
			this.mouseY = y;
		}
		
		@Override
		public boolean test(IRenderer renderer) {	
			int guiScale = mc.gameSettings.guiScale;
			double scaleFactor = 1;
			
			switch (guiScale) {
			case 1:
				scaleFactor = 4;
				break;
			case 2:
				scaleFactor = 2;
				break;
			case 3:
				scaleFactor = 1.33;
				break;
			}
			
			HashMap<String, Integer> settings = renderer.getSettings().getSettings();
			float floatScale;
			
			if (settings.containsKey("SIZE")) {
				floatScale = (float)settings.get("SIZE")/(float)Constants.FLOAT_SCALE;
				scaleFactor *= floatScale;
			}
			
			ScreenPosition pos = renderers.get(renderer);
			
			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();
			
			if (mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth() * scaleFactor && mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight() * scaleFactor) {
				return true;
			}
			
			return false;
		}
		
	}
}