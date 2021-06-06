package GandyClient.events.impl;

import GandyClient.events.EventCancellable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class DrawBlockOutlineEvent extends EventCancellable {
	
	private EntityPlayer player;
	private MovingObjectPosition position;
	private float partialTicks;
	
	public DrawBlockOutlineEvent (EntityPlayer player, MovingObjectPosition position, float partialTicks) {
		this.player = player;
		this.position = position;
		this.partialTicks = partialTicks;
	}
	
	public EntityPlayer getPlayer () {
		return this.player;
	}
	
	public MovingObjectPosition getPosition () {
		return this.position;
	}
	
	public float getPartialTicks () {
		return this.partialTicks;
	}
	
}
