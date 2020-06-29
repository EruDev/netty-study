package com.eru.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊服务端 简单实现
 * Created by eru on 2020/6/29.
 */
public class GroupChatServer {

    private static final int PORT = 6666;

    private Selector selector;

    private ServerSocketChannel listenChannel;

    public GroupChatServer(){
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            // 绑定到指定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将该channel 注册到 selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listen(){
        while (true){
            try {
                int count = selector.select(2000);
                if (count > 0){
                    Iterator<SelectionKey> ski = selector.selectedKeys().iterator();
                    while (ski.hasNext()){
                        SelectionKey key = ski.next();

                        // 客户端连接
                        if (key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + " 上线");
                        }
                        if (key.isReadable()){
                            readData(key);
                        }
                    }
                }else{
                    System.out.println("等待...");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void readData(SelectionKey key) {
        SocketChannel channel = null;

        try {
            channel = (SocketChannel)key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if (count > 0){
                String msg = new String(byteBuffer.array());
                System.out.println("From Client" + msg);
                // 转发消息给其他客户端
                sendInfoToOtherClients(msg, channel);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务端转发消息...");

        for (SelectionKey key : selector.selectedKeys()) {
            //通过 key  取出对应的 SocketChannel
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self){
                SocketChannel socketChannel = (SocketChannel)targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
