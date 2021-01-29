package com.snebot.fbmoll.communication.channel;

import com.snebot.fbmoll.helper.BallTaskHelper;
import com.snebot.fbmoll.thread.ThreadedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Channel extends ThreadedObject {
    private static final Logger log = LoggerFactory.getLogger(Channel.class);
    private Socket socket = null;

    public Channel() {
        start();
    }

    public boolean isOk() {
        return this.socket != null;
    }

    public synchronized boolean assignSocket(Socket socket) {
        if (this.socket != null) return false;
        this.socket = socket;
        return true;
    }

    public synchronized void removeSocket() {
        this.socket = null;
    }

    public void send(String str) {
        if (this.socket == null) return;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            writer.write(str);
            writer.flush();
            log.info("send: " + str);
        } catch (Exception e) {
            log.error("failed to send data: ", e);
        }
    }

    @Override
    public void run() {
        while (this.run) {
            BufferedReader reader = null;
            try {
                while (this.socket != null && !this.socket.isClosed()) {
                    if (reader == null)
                        reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    String line = reader.readLine();
                    if (line != null) log.info("received: " + line);
                }
            } catch (Exception e) {
                log.error("Channel thread failed: ", e);
            } finally {
                BallTaskHelper.close(reader);
            }
        }
        BallTaskHelper.close(this.socket);
    }
}