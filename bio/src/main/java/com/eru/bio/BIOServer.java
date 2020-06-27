package com.eru.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO DEMO
 * Created by eru on 2020/6/27.
 */
public class BIOServer {

    /** 监听的端口 */
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("服务端启动成功, 监听端口：" + PORT);

        while (true){
            final Socket socket = serverSocket.accept();

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(socket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    /**
     * 读取客户端发来的数据
     * @param socket 客户端连接
     */
    private static void handler(Socket socket) throws Exception {
        InputStream in = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int data;

        while ((data = in.read(buffer)) != -1){
            String msg = new String(buffer, 0, data);
            System.out.println("客户端消息：【" + msg + "】");
        }
    }
}
