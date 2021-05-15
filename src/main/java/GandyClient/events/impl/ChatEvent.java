package GandyClient.events.impl;

import GandyClient.events.Event;
import net.minecraft.util.IChatComponent;

public class ChatEvent extends Event {
	private final IChatComponent chat;

    public ChatEvent(IChatComponent chat) {
        this.chat = chat;
    }

    public IChatComponent getChat() {
        return chat;
    }
}