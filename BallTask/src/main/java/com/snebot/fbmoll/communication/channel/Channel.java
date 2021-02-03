package com.snebot.fbmoll.communication.channel;

import com.snebot.fbmoll.communication.data.Packet;
import com.snebot.fbmoll.helper.BallTaskHelper;
import com.snebot.fbmoll.thread.ThreadedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Channel extends ThreadedObject {
    private static final Logger log = LoggerFactory.getLogger(Channel.class);
    private ChannelHealth health = new ChannelHealth(this);
    protected Socket socket = null;
    private ChannelDelegate delegate = null;

    public Channel(ChannelDelegate delegate) {
        this.delegate = delegate;
        start();
    }

    public boolean isOk() {
        return this.socket != null;
    }

    public synchronized boolean assignSocket(Socket socket) {
        if (this.socket != null && this.socket.equals(socket)) return true;
        if (this.socket != null) return false;
        this.socket = socket;
        return true;
    }

    public synchronized void removeSocket() {
        BallTaskHelper.close(this.socket);
        this.socket = null;
        log.info("removed socket");
    }

    public boolean send(Packet packet) {
        if (this.socket == null || this.socket.isClosed()) return false;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            String content = BallTaskHelper.marshallJSON(packet);
            writer.write(content + "\n");
            writer.flush();
            return true;
        } catch (Exception e) {
            log.error("failed to send data: ", e);
            return false;
        }
    }

    @Override
    public void run() {
        while (this.run) {
            BufferedReader reader = null;
            try {
                while (isOk() && !this.socket.isClosed()) {
                    if (reader == null)
                        reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    String line = reader.readLine();
                    if (line != null) {
                        Packet packet = BallTaskHelper.unmarshallJSON(line, Packet.class);
                        if (packet != null) {
                            if (Packet.BALL_TASK_GREETING.equals(packet.getHeader())) {
                                log.info("received greeting msg");
                            } else if (Packet.ACK_HEADER.equals(packet.getHeader())) {
                                send(Packet.headerYES());
                            } else if (Packet.YES_HEADER.equals(packet.getHeader())) {
                                this.health.setACK(true);
                            } else {
                                if (this.delegate != null)
                                    this.delegate.didReceiveBall(packet.getBall(), packet.position);
                            }
                        }
                    }
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