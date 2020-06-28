package com.eru.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 利用FileChannel读写数据
 * Created by eru on 2020/6/28.
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("./file.txt");
        FileChannel fileChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("./file-copy.txt");
        FileChannel fileChannel2 = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true){
            // 重置标记
            buffer.clear();
            int datLen = fileChannel.read(buffer);
            if (datLen == -1){
                break;
            }

            buffer.flip();
            fileChannel2.write(buffer);
        }


        inputStream.close();
        outputStream.close();
    }
}
