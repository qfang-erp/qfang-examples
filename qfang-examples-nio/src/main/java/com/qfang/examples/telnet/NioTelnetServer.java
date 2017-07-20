package com.qfang.examples.telnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * nio + thread pool
 * Created by walle on 2017/3/12.
 */
public class NioTelnetServer {

    public static void main(String[] args) throws IOException {
        NioTelnetServer.start();
    }

    public static void start() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(9090);
        serverSocketChannel.socket().bind(address);
        System.out.println("telnet server start at " + address);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int selectNum = selector.select();
            if(selectNum == 0)
                continue;
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if(key.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    socketChannel.write(ByteBuffer.wrap("welcome telnet server ... \r\n".getBytes()));
                } else if(key.isReadable()) {
                    System.out.println(key);
                    executorService.execute(new ReadEventHandler(key));
                    key.interestOps(key.interestOps()&(~SelectionKey.OP_READ));
                } else if(key.isWritable()) {
                    System.out.println(key);
                    executorService.execute(new WriteEventHandler(key));
                }
                keyIterator.remove();
            }
        }
    }

    private static class ReadEventHandler implements Runnable {

        private final SelectionKey selectionKey;

        private ReadEventHandler(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(100);
                buffer.put("\r\nFollow you: ".getBytes(Charset.forName("UTF-8")));
                int size = socketChannel.read(buffer);
                buffer.put("\r\n".getBytes(Charset.forName("UTF-8")));
                buffer.flip();
                socketChannel.write(buffer);
                if(size == -1) {  // 链接关闭
                    socketChannel.close();
                } else {
                    selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_READ);
                    selectionKey.selector().wakeup();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class WriteEventHandler implements Runnable {

        private final SelectionKey selectionKey;

        private WriteEventHandler(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            System.out.println("receive write event");
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            selectionKey.attachment();
        }
    }

}
