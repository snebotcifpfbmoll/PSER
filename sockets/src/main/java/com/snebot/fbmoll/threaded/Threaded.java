package com.snebot.fbmoll.threaded;

import com.snebot.fbmoll.Client;

public class Threaded {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();

        for (int i = 0; i < 5; i++) {
            Client client = new Client();
            client.start();
        }
    }
}
