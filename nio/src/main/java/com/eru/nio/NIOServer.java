package com.eru.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 服务端
 * Created by eru on 2020/6/29.
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        // 注册到 selector, 设置事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            if (selector.select(1000) == 0){
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()){
                    SocketChannel channel = (SocketChannel)key.channel();

                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
                    channel.read(byteBuffer);

                    System.out.println("from 客户端" + new String(byteBuffer.array()));
                }
                keyIterator.remove();
            }
        }
    }
}
