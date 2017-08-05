package com.qfang.examples.timerserver.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @date 2017/7/31
 * @since 1.0
 */
public class AIOTimeClient {
    public static void main(String[] args) {
        ExecutorService service= Executors.newFixedThreadPool(10);


        List<Callable<Object>> runnables=new LinkedList<>();
        for (int i =0 ;i<100;i++){
            runnables.add(()->{
                try {
                    final AsynchronousSocketChannel  asChannel=AsynchronousSocketChannel.open();
                    asChannel.connect(new InetSocketAddress("127.0.0.1", 9999),null, new CompletionHandler<Void, Object>() {
                        @Override
                        public void completed(Void result, Object attachment) {
                            asChannel.write(ByteBuffer.wrap("what's then time".getBytes()),null, new CompletionHandler<Integer,Object>() {
                                @Override
                                public void completed(Integer result, Object attachment) {

                                    ByteBuffer buffer =ByteBuffer.allocate(1024);
                                    asChannel.read(buffer,buffer,new CompletionHandler<Integer,ByteBuffer>(){

                                        @Override
                                        public void completed(Integer result, ByteBuffer attachment) {
                                            attachment.flip();
                                            System.out.println("server now is:"+new String(attachment.array()));
                                            try {
                                                asChannel.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void failed(Throwable exc, ByteBuffer attachment) {

                                        }
                                    });
                                }

                                @Override
                                public void failed(Throwable exc, Object attachment) {

                                }
                            });

                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            });
        }

        try {
            List<Future<Object>> futures = service.invokeAll(runnables);
            for (Future<Object>  future:futures) {
                future.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
