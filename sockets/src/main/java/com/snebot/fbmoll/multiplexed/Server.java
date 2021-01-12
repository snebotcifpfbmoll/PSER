package com.snebot.fbmoll.multiplexed;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private int port = 1234;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Server() {
    }

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            ServerSocket serverSocket = serverChannel.socket();
            serverSocket.bind(new InetSocketAddress(this.port));

            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                    } else if (key.isReadable()) {
                    } else {
                        System.out.printf("[%s] Could not process selection key.", this.thread.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void newChannel(SelectionKey key, Selector selector) {
        try {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFromChannel(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
    }
}
