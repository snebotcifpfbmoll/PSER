package com.snebot.fbmoll.communication.channel;

import com.snebot.fbmoll.thread.ThreadedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelHealth extends ThreadedObject {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Channel channel;
    public int delay = 1000;

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
                Thread.sleep(delay);
                if (!this.channel.isOk()) continue;
                if (!this.channel.checkConnection(this.delay)) this.channel.removeSocket();
            }
        } catch (Exception e) {
            log.error("ChannelHealth thread crashed");
        }
    }
}