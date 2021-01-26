package com.snebot.fbmoll.communication;

import com.snebot.fbmoll.thread.ThreadedObject;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends ThreadedObject {
    private static final int PORT = 42069;

    @Override
    public void run() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (this.run) {
                clientSocket = serverSocket.accept();
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                System.out.println("New connection from: " + clientAddress);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
