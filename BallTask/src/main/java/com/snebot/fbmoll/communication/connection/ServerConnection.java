package com.snebot.fbmoll.communication.connection;

import com.snebot.fbmoll.communication.channel.Channel;
import com.snebot.fbmoll.helper.BallTaskHelper;
import com.snebot.fbmoll.thread.ThreadedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends ThreadedObject {
    private static final Logger log = LoggerFactory.getLogger(ServerConnection.class);
    private int port = 3411;
    private Channel channel = null;

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

    public ServerConnection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            log.info("Listening on port: " + port);
            while (this.run) {
                Socket clientSocket = serverSocket.accept();
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                log.info("New connection from: " + clientAddress);
                new IdentifiedConnection(clientSocket, this.channel);
            }
        } catch (Exception e) {
            log.error("error in ServerConnection thread: ", e);
        } finally {
            BallTaskHelper.close(serverSocket);
        }
    }
}
