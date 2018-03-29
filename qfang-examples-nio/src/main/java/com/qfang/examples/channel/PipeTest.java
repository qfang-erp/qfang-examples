package com.qfang.examples.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * Java NIO Pipe是2个线程之间的单向数据连接。
 * 在多线程编程中除了wait(), notify(), notifyAll()等，增加了一种新的线程间通讯方式。
 * Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
 * 例如，数据通过Thread A 写入 sink通道，然后通过Thread B 读取source通道获得数据。
 *
 * @author: liaozhicheng
 * @date: 2018-03-29
 * @since: 1.0
 */
public class PipeTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Pipe pipe = Pipe.open();

        Thread t1 = new Thread(new MyRunnable(pipe) {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    try {
                        int t = random.nextInt(1000);
                        Thread.sleep(t);

                        send();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void send() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = "current time : " + simpleDateFormat.format(new Date());
                ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                byteBuffer.put(time.getBytes());
                byteBuffer.flip();

                try {
                    Pipe.SinkChannel sinkChannel = pipe.sink();
                    while (byteBuffer.hasRemaining()) {
                        sinkChannel.write(byteBuffer);
                    }

                    System.out.println(time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "send-thread");
        t1.start();

        Thread t2 = new Thread(new MyRunnable(pipe) {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);

                        Pipe.SourceChannel sourceChannel = pipe.source();

                        ByteBuffer readBuffer = ByteBuffer.allocate(512);
                        sourceChannel.read(readBuffer);

                        readBuffer.flip();
                        if(readBuffer.hasRemaining()) {
                            byte[] readByte = new byte[readBuffer.remaining()];
                            readBuffer.get(readByte);
                            String receiver = new String(readByte);
                            System.out.println("receiver message: " + receiver);

                            Pipe.SinkChannel sinkChannel = pipe.sink();
                            ByteBuffer buffer = ByteBuffer.wrap(readByte);
                            buffer.flip();
                            while (buffer.hasRemaining()) {
                                sinkChannel.write(buffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "receiver-thread");
        t2.start();

        t1.join();
        t2.join();
    }

    static abstract class MyRunnable implements Runnable {

        final Pipe pipe;

        protected MyRunnable(Pipe pipe) {
            this.pipe = pipe;
        }


    }

}
