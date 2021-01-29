package com.snebot.fbmoll.communication.connection;

import com.snebot.fbmoll.communication.channel.Channel;
import com.snebot.fbmoll.communication.data.Packet;
import com.snebot.fbmoll.helper.BallTaskHelper;
import com.snebot.fbmoll.thread.ThreadedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.net.Socket;

public class ClientConnection extends ThreadedObject {
    private static final Logger log = LoggerFactory.getLogger(ClientConnection.class);
    private static final int MIN_TIMEOUT = 100;
    private static final int MAX_TIMEOUT = 600;
    private String ip = "127.0.0.1";
    private int port = 3411;
    private Channel channel = null;
    public int connection_delay = BallTaskHelper.random(MIN_TIMEOUT, MAX_TIMEOUT);

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ClientConnection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            while (this.run) {
                Thread.sleep(this.connection_delay);
                if (this.channel.isOk()) continue;
                try {
                    socket = new Socket(ip, port);
                } catch (ConnectException e) {
                    //log.error("failed to connect to server", e);
                }
                if (socket != null) {
                    if (!this.channel.assignSocket(socket)) {
                        BallTaskHelper.close(socket);
                        socket = null;
                    } else {
                        Packet packet = new Packet();
                        packet.setHeader("BallTask");
                        this.channel.send(packet);
                    }
                }
            }
        } catch (Exception e) {
            log.error("ClientConnection thread failed", e);
        } finally {
            BallTaskHelper.close(socket);
        }
    }
}