package com.qfang.examples.timerserver.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author huxianyong
 * @date 2017/7/31
 * @since 1.0
 */
public class AIOTimeServer {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final int PORT=9999;
    private AsynchronousServerSocketChannel  aSSChannel;

    public AIOTimeServer() throws IOException {
        this.aSSChannel =AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
        System.out.println("The time server is start in port : "+PORT);
    }
    

    public void server(){
        aSSChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println( " currentThread name："+Thread.currentThread().getName());
                Future<Integer> future=null;
                try {
                    ByteBuffer buffer=ByteBuffer.allocate(1024);
                    result.read(buffer).get(200,TimeUnit.SECONDS);
                    System.out.println("client message :"+new String (buffer.array()));
                    buffer.clear();
                    buffer.put(LocalDateTime.now().format(formatter).getBytes());
                    buffer.flip();
                    future=result.write(buffer);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        aSSChannel.accept(null, this);
                        future.get();
                        result.close();
                    }catch (Exception  e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("exception "+exc.getMessage());
            }
        });

    }

    public static void main(String[] args) {
       // System.out.println(LocalDateTime.now().format(formatter).getBytes());
        try {
            AIOTimeServer server=new AIOTimeServer();
            server.server();
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
