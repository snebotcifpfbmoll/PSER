package com.snebot.fbmoll.communication;

import org.junit.jupiter.api.Test;

public class ServerClientTest {
    @Test
    void testServerClient() {
        ServerConnection server = new ServerConnection();
        ClientConnection client = new ClientConnection();
        server.start();
        client.start();
        System.out.println(client.join(10000));
    }
}
