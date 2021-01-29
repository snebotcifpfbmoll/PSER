package com.snebot.fbmoll.communication.channel;

import com.snebot.fbmoll.communication.data.Packet;
import com.snebot.fbmoll.thread.ThreadedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelHealth extends ThreadedObject {
    private static final String ACK_HEADER = "ACK";
    private static final String YES_HEADER = "YES";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Channel channel;
    private boolean connected = false;
    public int delay = 1000;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelHealth(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            while (this.run && this.channel != null) {
                this.connected = false;
                this.channel.send(new Packet(ACK_HEADER));
                Thread.sleep(delay);
                if (!this.connected) this.channel.removeSocket();
            }
        } catch (Exception e) {
            log.error("ChannelHealth thread crashed");
        }
    }
}