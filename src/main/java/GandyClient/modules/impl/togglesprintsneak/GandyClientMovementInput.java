package GandyClient.modules.impl.togglesprintsneak;

import java.text.DecimalFormat;

import GandyClient.modules.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class GandyClientMovementInput extends MovementInput {
	private GameSettings gameSettings;
	private int sneakWasPressed = 0;
	private int sprintWasPressed = 0;
	private EntityPlayerSP player;
	private float originalFlySpeed = -1.0F;
	private float boostedFlySpeed = 0;
	private Minecraft mc;
	private ModToggleSprintSneak reference;
	
	public GandyClientMovementInput(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
		this.mc = Minecraft.getMinecraft();
		this.reference = ModInstances.getModToggleSprintSneak();
	}
	
	@Override
	public void updatePlayerMoveState() {
		player = mc.thePlayer;
		moveStrafe = 0.0F;
		moveForward = 0.0F;
		
		if (gameSettings.keyBindForward.isKeyDown()) {
			moveForward++;
		}
		if (gameSettings.keyBindBack.isKeyDown()) {
			moveForward--;
		}
		if (gameSettings.keyBindLeft.isKeyDown()) {
			moveStrafe++;
		}
		if (gameSettings.keyBindRight.isKeyDown()) {
			moveStrafe--;
		}
		
		jump = gameSettings.keyBindJump.isKeyDown();

		if(ModInstances.getModToggleSprintSneak().isEnabled()) {
			if (gameSettings.keyBindSneak.isKeyDown()) {
				if (sneakWasPressed == 0) {
					if (sneak) {
						sneakWasPressed = -1;
					} else if (player.isRiding() || player.capabilities.isFlying) {
						sneakWasPressed = ModInstances.getModToggleSprintSneak().keyHoldTicks + 1;
					} else {
						// sneakWasPressed = 1;
						// edit to disable togglesneak
						sneakWasPressed = ModInstances.getModToggleSprintSneak().keyHoldTicks + 1;
					}
					
					sneak = !sneak;
				} else if (sneakWasPressed > 0) {
					sneakWasPressed++;
				}
			} else {
				if (
						(ModInstances.getModToggleSprintSneak().keyHoldTicks > 0) && 
						(sneakWasPressed > ModInstances.getModToggleSprintSneak().keyHoldTicks)
				) {
					sneak = false;
				}
				sneakWasPressed = 0;
			}
		} else {
			sneak = gameSettings.keyBindSneak.isKeyDown();
		}
		
		if (sneak) {
			moveStrafe *= 0.3F;
			moveForward *= 0.3F;
		}

		// SPRINT
		if (ModInstances.getModToggleSprintSneak().isEnabled()) {
			if (gameSettings.keyBindSprint.isKeyDown()) {
				if (sprintWasPressed == 0) {
					
					if (reference.sprint) {
						sprintWasPressed = -1;
					} else if (player.capabilities.isFlying) {
						sprintWasPressed = reference.keyHoldTicks + 1;
					} else {
						sprintWasPressed = 1;
					}
					
					reference.setSprint(!ModInstances.getModToggleSprintSneak().sprint);
					
				} else if (sprintWasPressed > 0) {
					sprintWasPressed++;
				}
			} else {
				if (
						(ModInstances.getModToggleSprintSneak().keyHoldTicks > 0) &&
						(sprintWasPressed > ModInstances.getModToggleSprintSneak().keyHoldTicks)
					) {
							reference.setSprint(false);
					}
				sprintWasPressed = 0;
			}
		} else {
			reference.setSprint(false);
		}
		
		if (reference.sprint && player.onGround && !player.isUsingItem() && !player.isPotionActive(Potion.blindness)) {
			player.setSprinting(true);
		}
		
		if (
				ModInstances.getModToggleSprintSneak().flyBoost && 
				player.capabilities.isCreativeMode &&
				player.capabilities.isFlying &&
				(mc.getRenderViewEntity() == player) &&
				reference.sprint
		) {
			
			if (originalFlySpeed < 0.0F || this.player.capabilities.getFlySpeed() != boostedFlySpeed) {
				originalFlySpeed = this.player.capabilities.getFlySpeed();
			}
			
			boostedFlySpeed = originalFlySpeed * ModInstances.getModToggleSprintSneak().flyBoostFactor;
			player.capabilities.setFlySpeed(boostedFlySpeed);
			
			if (sneak) {
				player.motionY -= 0.15D * (double)(ModInstances.getModToggleSprintSneak().flyBoostFactor - 1.0F);
			}
			if (jump) {
				player.motionY -= 0.15D * (double)(ModInstances.getModToggleSprintSneak().flyBoostFactor - 1.0F);
			}
		} else {
			if (player.capabilities.getFlySpeed() == boostedFlySpeed) {
				this.player.capabilities.setFlySpeed(originalFlySpeed);
			}
			
			originalFlySpeed = -1.0F;
		}
		
	}
	
	private static final DecimalFormat df = new DecimalFormat("#.0");
	public String getDisplayText () {
		String displayText = "";
		
		boolean isFlying = mc.thePlayer.capabilities.isFlying;
		boolean isRiding = mc.thePlayer.isRiding();
		boolean isHoldingSneak = gameSettings.keyBindSneak.isKeyDown();
		boolean isHoldingSprint = gameSettings.keyBindSprint.isKeyDown();
		
		if (isFlying) {
			if (originalFlySpeed > 0) {
				displayText += "[Flying (" + df.format(boostedFlySpeed/originalFlySpeed) + "x Boost)]  "; 
			} else {
				displayText += "[Flying]  ";
			}
		}
		
		if (isRiding) {
			displayText += "[Riding]  ";
		}
		if (sneak) {
			if (isFlying) {
				displayText += "[Descending]  ";
			} else if (isRiding) {
				displayText += "[Dismounting]  ";
			} else if (isHoldingSneak) {
				displayText += "[Sneaking (Key Held)]  "; 
			} else {
				displayText += "[Sneaking (Key Toggled)]  ";
			}
		} else if (reference.sprint && !isFlying && !isRiding) {
			if (isHoldingSprint) {
				displayText += "[Sneaking (Key Held)]  "; 
			} else {
				displayText += "[Sprinting (Key Toggled)]  ";
			}
		}
		
		return displayText.trim();
	}
}
