package com.snebot.fbmoll.communication;

import com.snebot.fbmoll.communication.channel.Channel;
import com.snebot.fbmoll.communication.connection.ClientConnection;
import com.snebot.fbmoll.communication.connection.ServerConnection;
import org.junit.jupiter.api.Test;

public class ServerClientTest {
    @Test
    void testServerClient() {
        Channel channel1 = new Channel(null);
        ServerConnection server1 = new ServerConnection(channel1);
        server1.setPort(4311);
        ClientConnection client1 = new ClientConnection(channel1);
        client1.setIp("127.0.0.1");
        client1.setPort(4312);

        Channel channel2 = new Channel(null);
        ServerConnection server2 = new ServerConnection(channel2);
        server2.setPort(4312);
        ClientConnection client2 = new ClientConnection(channel2);
        client2.setIp("127.0.0.1");
        client2.setPort(4311);

        server1.start();
        client1.start();
        server2.start();
        client2.start();

        System.out.println(client1.join(2000));
        System.out.println(client2.join(2000));
    }
}
