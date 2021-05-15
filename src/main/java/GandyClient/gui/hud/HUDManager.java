package GandyClient.gui.hud;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.google.common.collect.Sets;

import GandyClient.events.EventManager;
import GandyClient.events.EventTarget;
import GandyClient.events.impl.RenderEvent;
import GandyClient.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;

public class HUDManager {

	private HUDManager () {}
	
	private static HUDManager instance = null;
	
	public static HUDManager getInstance () {
		if (instance != null) {
			return instance;
		}
		
		instance = new HUDManager();
		EventManager.register(instance);
		
		return instance;
	}
	
	private Set<IRenderer> registeredRenderers = Sets.newHashSet();
	private Minecraft mc = Minecraft.getMinecraft();
	
	public void register(IRenderer... renderers) {
		for (IRenderer render : renderers) {
			this.registeredRenderers.add(render);
		}
	}
	
	public void unregister (IRenderer... renderers) {
		for (IRenderer render : renderers) {
			this.registeredRenderers.remove(render);
		}
	}
	
	public boolean has (IRenderer renderer) {
		return this.registeredRenderers.contains(renderer);
	}
	
	public Collection<IRenderer> getRegisteredRenderers () {
		return Sets.newHashSet(registeredRenderers);
	}
	
	public void openConfigScreen () {
		mc.displayGuiScreen(new HUDConfigScreen(this));
	}
	
	@EventTarget
	public void onRender (RenderEvent e) {
		if (mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiChat) {
			for (IRenderer renderer : registeredRenderers) {
				callRenderer(renderer);
			}
		}
	}
	
	private void adjustBounds (IRenderer renderer, ScreenPosition pos) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int screenWidth = res.getScaledWidth();
		int screenHeight = res.getScaledHeight();
		
		int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
		int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));
		
		pos.setAbsolute(absoluteX, absoluteY);
	}

	private void callRenderer(IRenderer renderer) {
		if (!renderer.isEnabled()) return;
		ScreenPosition pos = renderer.load();
		
		if (pos == null) {
				pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
		}
		
		if (!renderer.isUseGl()) {
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
		
			GlStateManager.pushMatrix();
			HashMap<String, Integer> settings = renderer.getSettings().getSettings();
			float floatScale;
			
			if (settings.containsKey("SIZE")) {
				floatScale = (float)settings.get("SIZE")/(float)Constants.FLOAT_SCALE;
				scaleFactor *= floatScale;
			}
			
			GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);
			ScreenPosition bufferPos = ScreenPosition.fromRelativePosition(pos.getRelativeX(), pos.getRelativeY());
			bufferPos.setAbsolute((int) (bufferPos.getAbsoluteX()/scaleFactor), (int) (bufferPos.getAbsoluteY()/scaleFactor));
			adjustBounds(renderer, bufferPos);
			renderer.render(bufferPos);
			GlStateManager.popMatrix();
		} else {
			adjustBounds(renderer, pos);
			renderer.render(pos);
		}
		
	}
}
