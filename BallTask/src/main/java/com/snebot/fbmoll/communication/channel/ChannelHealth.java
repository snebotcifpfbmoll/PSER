package com.snebot.fbmoll.communication.channel;

import com.snebot.fbmoll.communication.data.Packet;
import com.snebot.fbmoll.thread.ThreadedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelHealth extends ThreadedObject {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Channel channel;
    private boolean ack = false;
    public int delay = 600;
    public int ack_retries = 5;

    public synchronized void setACK(boolean ack) {
        this.ack = ack;
    }

    public boolean getACK() {
        return this.ack;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelHealth(Channel channel) {
        this.channel = channel;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (this.run && this.channel != null) {
                Thread.sleep(this.delay);
                log.info("isOK: " + this.channel.isOk());
                if (this.channel.isOk()) {
                    setACK(false);
                    this.channel.send(Packet.headerACK());
                    for (int i = 0; i < this.ack_retries && !this.ack; i++)
                        Thread.sleep(this.delay);
                    if (!this.ack)
                        this.channel.removeSocket();
                }
            }
        } catch (Exception e) {
            log.error("ChannelHealth thread crashed");
        }
    }
}