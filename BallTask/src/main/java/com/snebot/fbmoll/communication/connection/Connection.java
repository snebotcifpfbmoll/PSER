package com.snebot.fbmoll.communication.connection;

import com.snebot.fbmoll.communication.channel.Channel;

import java.net.Socket;

public interface Connection {
    Socket socket = null;
    Channel channel = null;
}
