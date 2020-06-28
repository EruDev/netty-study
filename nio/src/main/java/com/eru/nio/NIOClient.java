package com.eru.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by eru on 2020/6/29.
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);

        if (!socketChannel.connect(address)){
            while (!socketChannel.finishConnect()){
                System.out.println("");
            }
        }

        String s = "from client";
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
