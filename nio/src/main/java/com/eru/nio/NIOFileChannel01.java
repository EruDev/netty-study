package com.eru.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 利用FileChannel写数据
 * Created by eru on 2020/6/28.
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception {
        String word = "hello,world";
        FileOutputStream outputStream = new FileOutputStream("./file.txt");
        FileChannel fileChannel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(word.getBytes());

        // 翻转，此时缓冲区 position 指向的是字节数组中的最后一个 也就是 'd'
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        outputStream.close();
    }
}
