package com.eru.nio;


import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel读数据
 * Created by eru on 2020/6/28.
 */
public class NioFileChannel02 {
    public static void main(String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("./file.txt");
        FileChannel fileChannel = inputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 将数据读入到 buffer
        int dataLen = fileChannel.read(buffer);
        String word = new String(buffer.array(), 0, dataLen);
        inputStream.close();
        System.out.println(word);
    }
}
