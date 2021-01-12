package com.snebot.fbmoll.threaded;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private Socket socket = null;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            if (this.socket != null) {
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
                String line = null;
                boolean done = false;
                do {
                    line = in.readLine();
                    if (line == null || line.equals("")) done = true;
                    System.out.printf("[%s] %s: %s\n", this.thread.getName(), this.socket.getInetAddress().getHostAddress(), line);
                    out.println(line);
                } while (!done);

                this.socket.close();
                System.out.printf("[%s] Connection with client closed.\n", this.thread.getName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (this.socket != null) this.socket.close();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
