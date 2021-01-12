package com.snebot.fbmoll.threaded;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private final List<ConnectionHandler> connections = new ArrayList<>();
    private int port = 1234;

    public List<ConnectionHandler> getConnections() {
        return connections;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Server() {
    }

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                System.out.printf("[%s] Listening on port: %d\n", this.thread.getName(), this.port);
                Socket clientSocket = serverSocket.accept();
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                System.out.printf("[%s] New connection from: %s\n", this.thread.getName(), clientAddress);

                ConnectionHandler handler = new ConnectionHandler(clientSocket);
                connections.add(handler);
                handler.start();
                System.out.printf("[%s] Added new connection handler.\n", this.thread.getName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
