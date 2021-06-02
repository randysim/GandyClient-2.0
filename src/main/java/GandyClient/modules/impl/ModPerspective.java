package GandyClient.modules.impl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import GandyClient.events.EventTarget;
import GandyClient.events.impl.ClientTickEvent;
import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;
import GandyClient.utils.Keybinds;

public class ModPerspective extends ModDraggable {
	
	private String type = "Perspective";
	private boolean isUseGl = false;
	private Setting settings;
	private float cameraYaw = 0F;
	private float cameraPitch = 0F;
	private int previousPerspective = 0;
	private boolean returnOnRelease = true;
	private boolean perspectiveToggled = false;
	private boolean pressed = false;
	
	public ModPerspective () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		if (!settings.getSettings().containsKey("INVERT_Y")) {
			settings.updateSetting("INVERT_Y", 0);
		}
	}
	
	@EventTarget
	public void keyboardEvent (ClientTickEvent e) {
		if (settings.getSettings().get("ENABLED") == 0) return;
		
		if (
				!Keybinds.CLIENT_PERSPECTIVE.isKeyDown() &&  
				pressed 
		) {
				pressed = false;
				perspectiveToggled = false;
				mc.gameSettings.thirdPersonView = previousPerspective;
		}
		
		if (Keybinds.CLIENT_PERSPECTIVE.isKeyDown() && !pressed) {
				pressed = true;
				perspectiveToggled = !perspectiveToggled;
				
				cameraYaw = mc.thePlayer.rotationYaw;
				cameraPitch = mc.thePlayer.rotationPitch;
				
				if (perspectiveToggled) {
					previousPerspective = mc.gameSettings.thirdPersonView;
					mc.gameSettings.thirdPersonView = 1;
				} else {
					mc.gameSettings.thirdPersonView = previousPerspective;
				}
			}
	}
	
	public float getCameraYaw () {
		return perspectiveToggled ? cameraYaw : mc.thePlayer.rotationYaw;
	}
	public float getCameraPitch () {
		return perspectiveToggled ? cameraPitch : mc.thePlayer.rotationPitch;
	}
	public void setCameraYaw (float yaw) {
		cameraYaw = yaw;
	}
	public void setCameraPitch (float pitch) {
		cameraPitch = pitch;
	}
	public boolean isToggled () {
		return perspectiveToggled;
	}
	
	public boolean overrideMouse()
	{
		if(mc.inGameHasFocus && Display.isActive()) {
			
			if(!perspectiveToggled) {
				return true;
			}
			
			mc.mouseHelper.mouseXYChange();
			float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			float f2 = f1 * f1 * f1 * 8.0F;
			float f3 = (float) mc.mouseHelper.deltaX * f2;
			float f4 = (float) mc.mouseHelper.deltaY * f2;
			
			cameraYaw += f3 * 0.15F;
			if (settings.getSettings().get("INVERT_Y") == 0) {
				cameraPitch += f4 * 0.15F;
			} else {
				cameraPitch -= f4 * 0.15F;
			}
			
			
			if (cameraPitch > 90) cameraPitch = 90;
			if (cameraPitch < -90) cameraPitch = -90;
					
					
		}

		return false;
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return type;
	}
	@Override 
	public String getSimpleName () {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean isUseGl() {
		// TODO Auto-generated method stub
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		// TODO Auto-generated method stub
	}

	@Override
	public Setting getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}

}
