package com.snebot.fbmoll.communication;

import com.snebot.fbmoll.communication.channel.Channel;
import com.snebot.fbmoll.communication.connection.ClientConnection;
import com.snebot.fbmoll.communication.connection.ServerConnection;
import org.junit.jupiter.api.Test;

public class ServerClientTest {
    @Test
    void testServerClient() {
        ServerConnection server = new ServerConnection(new Channel());
        ClientConnection client = new ClientConnection(new Channel());
        server.start();
        client.start();
        System.out.println(client.join(10000));
    }
}
