package com.qfang.examples.proxy;

import io.netty.util.CharsetUtil;
import sun.nio.ch.DirectBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: liaozhicheng
 * @date: 2018-04-20
 * @since: 1.0
 */
public class NioHttpClient {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.bootcss.com", 80));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        for (; ; ) {
            selector.select(100);
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                handler(key, selector);
            }
        }
    }

    private static void handler(SelectionKey key, Selector selector) throws IOException {
        if (!key.isValid())
            return;

        SocketChannel channel = (SocketChannel) key.channel();
        if (key.isConnectable()) {
            if (channel.finishConnect()) {
                System.out.println("finishConnect ....");
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                channel.configureBlocking(false);

//                ByteBuffer request = ByteBuffer.wrap("xx".getBytes());
                ByteBuffer request = getRequestHead();
//                request.flip();

                System.out.println(request.hasRemaining());

                channel.write(request);

                System.out.println(request.hasRemaining());
            } else {
                channel.close();
            }
        } else if (key.isReadable()) {
            System.out.println("read data...");

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int length = channel.read(byteBuffer);
            if (length > 0) {
                byteBuffer.flip();
                byte[] readByte = new byte[byteBuffer.remaining()];
                byteBuffer.get(readByte);

                String str = new String(readByte, Charset.forName("UTF-8"));
                System.out.println(str);
            } else {
                key.cancel();
                channel.close();
            }

        }
    }

    private static ByteBuffer getRequestHead() throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(HttpProxy.class.getResourceAsStream("/request_header.txt")))
        ) {
            String headers = reader.lines().collect(Collectors.joining("\r\n"));
            System.out.println(headers);
            return ByteBuffer.wrap(headers.getBytes(CharsetUtil.UTF_8));
        }


    }

}
