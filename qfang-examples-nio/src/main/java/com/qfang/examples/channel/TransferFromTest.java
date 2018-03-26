package com.qfang.examples.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-27
 * @since: 1.0
 */
public class TransferFromTest {

    public static void main(String[] args) throws IOException {
        transferTo();
    }

    private static void transferFrom() throws IOException {
        String filePath = FileChannelTest.class.getResource("/test.txt").getPath();
        RandomAccessFile fromFile = new RandomAccessFile(filePath, "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("d:/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
    }

    private static void transferTo() throws IOException {
        String filePath = FileChannelTest.class.getResource("/test.txt").getPath();
        RandomAccessFile fromFile = new RandomAccessFile(filePath, "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("d:/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();
        fromChannel.transferTo(position, count, toChannel);
    }

}
