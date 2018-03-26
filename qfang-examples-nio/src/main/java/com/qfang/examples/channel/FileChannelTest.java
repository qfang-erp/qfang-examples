package com.qfang.examples.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-27
 * @since: 1.0
 */
public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        String filePath = FileChannelTest.class.getResource("/test.txt").getPath();
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        FileChannel fileChannel = file.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(10);
        int readBuff = fileChannel.read(buffer);
        while (readBuff != -1) {
            buffer.flip();

            while (buffer.hasRemaining()) {
                // byteBuffer#get 每次只读取当前 position 所在位置一个 byte
                System.out.println((char) buffer.get());
            }

            buffer.clear();
            readBuff = fileChannel.read(buffer);
        }
        file.close();
    }

}
