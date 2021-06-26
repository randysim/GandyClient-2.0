package GandyClient.core.discord;

import GandyClient.core.Client;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class DiscordIPC implements IPCListener {

    public static final DiscordIPC INSTANCE = new DiscordIPC();
    private IPCClient client;
    private boolean hasClient = true;

    public void init() {
        client = new IPCClient(835268116057555016L);
        client.setListener(this);
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            e.printStackTrace();
            hasClient = false;
            return;
            
        } catch (Exception e) {
            Client.error("UNKOWN ERROR");
            e.printStackTrace();
            hasClient = false;
            return;
        }
        Client.info("IPC {} -> {}", client.getStatus(), client.getDiscordBuild());
    }

    public void update(String state, String details) {
    	if (hasClient) {
    		RichPresence.Builder builder = new RichPresence.Builder()
                .setState(state)
                .setDetails(details)
                .setLargeImage("background", "Gandy Client")
                .setStartTimestamp(OffsetDateTime.now());
    		client.sendRichPresence(builder.build());
    	}
    }

    public void shutdown() {
        if (client != null && hasClient && client.getStatus() == PipeStatus.CONNECTED) {
            client.close();
            Client.info("Discord IPC closed!");
        }
    }

    @Override
    public void onReady(IPCClient client) {
        RichPresence.Builder builder = new RichPresence.Builder()
                .setState("Idle")
                .setDetails("Minecraft 1.8.9" )
                .setLargeImage("background", "Gandy Client")
                .setStartTimestamp(OffsetDateTime.now());
        client.sendRichPresence(builder.build());
    }

    @Override
    public void onClose(IPCClient client, JSONObject json) {

    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {

    }

}
