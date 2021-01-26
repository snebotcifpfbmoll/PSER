package com.snebot.fbmoll.communication;

import com.snebot.fbmoll.helper.BallTaskHelper;
import com.snebot.fbmoll.thread.ThreadedObject;

import java.net.ConnectException;
import java.net.Socket;

public class ClientConnection extends ThreadedObject {
    private static final String CONN_IP = "127.0.0.1";
    private static final int CONN_PORT = 3411;
    private static final int MIN_TIMEOUT = 100;
    private static final int MAX_TIMEOUT = 400;
    private int wait = BallTaskHelper.random(MIN_TIMEOUT, MAX_TIMEOUT);

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            while (this.run) {
                Thread.sleep(this.wait);
                System.out.println("Connecting...");
                try {
                    socket = new Socket(CONN_IP, CONN_PORT);
                } catch (ConnectException e) {
                    System.out.println("Connection refused.");
                }
                if (socket != null) this.run = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}