package com.snebot.fbmoll;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private String host = "localhost";
    private int port = 1234;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Client() {
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(this.host, this.port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            for (int i = 0; i < 10; i++) {
                Thread.sleep(100);
                out.println(i);
                System.out.printf("[%s] sending: %d\n", this.thread.getName(), i);
            }

            out.println("");
            System.out.printf("[%s] closed connection with server\n", this.thread.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
