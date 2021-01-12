package com.snebot.fbmoll.multiplexed;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class ClientInfo {
    private static final int MAX_BUFF_SIZE = 1024;
    private SocketChannel socketChannel;
    private Server server;
    private ByteBuffer inBuffer;
    private Charset charset;
    private CharsetDecoder charsetDecoder;

    public ClientInfo(SocketChannel socketChannel, Server server) {
        this.socketChannel = socketChannel;
        this.server = server;

        this.inBuffer = ByteBuffer.allocateDirect(MAX_BUFF_SIZE);
        this.inBuffer.clear();
        this.charset = StandardCharsets.UTF_8;
        this.charsetDecoder = this.charset.newDecoder();
    }

    public String read() {
        String inputMessage = null;
        try {
            int numBytesRead = socketChannel.read(this.inBuffer);
            if (numBytesRead == -1) {
                this.socketChannel.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return inputMessage;
    }
}
