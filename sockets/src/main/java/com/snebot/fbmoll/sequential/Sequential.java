package com.snebot.fbmoll.sequential;

import com.snebot.fbmoll.Client;

public class Sequential {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        Client client = new Client();
        client.start();
    }
}
