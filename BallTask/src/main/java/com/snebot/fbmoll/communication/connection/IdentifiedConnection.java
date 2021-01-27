package com.snebot.fbmoll.communication.connection;

import com.snebot.fbmoll.communication.channel.Channel;
import com.snebot.fbmoll.thread.ThreadedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

public class IdentifiedConnection extends ThreadedObject {
    private static final Logger log = LoggerFactory.getLogger(IdentifiedConnection.class);
    private Socket socket = null;
    private Channel channel = null;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public IdentifiedConnection(Socket socket, Channel channel) {
        this.socket = socket;
        this.channel = channel;
        start();
    }

    @Override
    public void run() {
        if (!this.channel.assignSocket(this.socket)) log.info("channel socket is already assigned");
    }
}
