package GandyClient.core.modules.impl;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import GandyClient.events.EventTarget;
import GandyClient.events.impl.ClientTickEvent;
import GandyClient.core.gui.hud.ScreenPosition;
import GandyClient.core.modules.ModDraggable;
import GandyClient.core.modules.Setting;
import GandyClient.core.utils.Keybinds;

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

    public ModPerspective() {
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
    public void keyboardEvent(ClientTickEvent e) {
        if (settings.getSettings().get("ENABLED") == 0) return;

        if (!Keybinds.CLIENT_PERSPECTIVE.isKeyDown() && pressed) {
            pressed = false;
            perspectiveToggled = false;
            Minecraft.getMinecraft().gameSettings.thirdPersonView = previousPerspective;
        }

        if (Keybinds.CLIENT_PERSPECTIVE.isKeyDown() && !pressed) {
            pressed = true;
            perspectiveToggled = !perspectiveToggled;

            cameraYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
            cameraPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

            if (perspectiveToggled) {
                previousPerspective = Minecraft.getMinecraft().gameSettings.thirdPersonView;
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
            } else {
                Minecraft.getMinecraft().gameSettings.thirdPersonView = previousPerspective;
            }
        }
    }

    public float getCameraYaw() {
        return perspectiveToggled ? cameraYaw : Minecraft.getMinecraft().thePlayer.rotationYaw;
    }

    public float getCameraPitch() {
        return perspectiveToggled ? cameraPitch : Minecraft.getMinecraft().thePlayer.rotationPitch;
    }

    public void setCameraYaw(float yaw) {
        cameraYaw = yaw;
    }

    public void setCameraPitch(float pitch) {
        cameraPitch = pitch;
    }

    public boolean isToggled() {
        return perspectiveToggled;
    }

    public boolean overrideMouse() {
        if (Minecraft.getMinecraft().inGameHasFocus && Display.isActive()) {

            if (!perspectiveToggled) {
                return true;
            }

            Minecraft.getMinecraft().mouseHelper.mouseXYChange();
            float f1 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8.0F;
            float f3 = (float) Minecraft.getMinecraft().mouseHelper.deltaX * f2;
            float f4 = (float) Minecraft.getMinecraft().mouseHelper.deltaY * f2;

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
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return type;
    }

    @Override
    public String getSimpleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean isUseGl() {
        return isUseGl;
    }

    @Override
    public void render(ScreenPosition pos) {
    }

    @Override
    public Setting getSettings() {
        return settings;
    }

}
