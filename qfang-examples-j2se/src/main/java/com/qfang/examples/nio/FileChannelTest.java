package com.qfang.examples.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.stream.IntStream;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/02/21 17:19
 */
public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        downloadMultiThread();
    }

    public static void copyFile() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("d:/temp/OAGIiASourceCode.zip", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("d:/temp/test2.zip", "rw");
        FileChannel toChannel = toFile.getChannel();

        toChannel.transferFrom(fromChannel, 0, fromChannel.size());
    }

    public static void downloadMultiThread() throws IOException {
        // 预分配文件所占的磁盘空间，磁盘中会创建一个指定大小的文件
        RandomAccessFile raf = new RandomAccessFile("D:/temp/abc.txt", "rw");
        raf.setLength(5 * 1024); // 预分配 5k 的文件空间
        raf.close();

        // 利用多线程同时写入一个文件
        IntStream.range(0, 5).forEach(i -> {
            new Thread(new FileWriteThread(1024 * i, ("第" + i + "个字符串").getBytes())).start();
        });
    }

    // 利用线程在文件的指定位置写入指定数据
    static class FileWriteThread implements Runnable {
        private int skip;
        private byte[] content;

        public FileWriteThread(int skip, byte[] content) {
            this.skip = skip;
            this.content = content;
        }

        @Override
        public void run() {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile("D:/temp/abc.txt", "rw");
                raf.seek(skip);
                raf.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    raf.close();
                } catch (Exception e) {
                }
            }
        }
    }


}
