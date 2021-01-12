package com.snebot.fbmoll.sequential;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private final Thread thread = new Thread(this);
    private int port = 1234;

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
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                System.out.println("Listening on port: " + port);
                clientSocket = serverSocket.accept();
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                System.out.println("New connection from: " + clientAddress);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                String line = null;
                boolean done = false;
                do {
                    line = in.readLine();
                    if (line == null || line.equals("")) done = true;
                    System.out.printf("[server] %s: %s\n", clientAddress, line);
                    out.println(line);
                } while (!done);

                clientSocket.close();
                System.out.println("Connection with client closed.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
                if (clientSocket != null) clientSocket.close();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
