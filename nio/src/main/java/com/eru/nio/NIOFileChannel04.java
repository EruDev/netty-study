package com.eru.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * transferTo 使用
 * Created by eru on 2020/6/28.
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("./src.png");
        FileChannel fileChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("./dst.png");
        FileChannel fileChannel2 = outputStream.getChannel();

        fileChannel.transferTo(0, fileChannel.size(), fileChannel2);

        inputStream.close();
        outputStream.close();
    }
}
