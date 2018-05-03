package com.qfang.examples.proxy;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: liaozhicheng
 * @date: 2018-04-20
 * @since: 1.0
 */
public class NioHttpClient {

    static List<byte[]> receiverBytes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.bootcss.com", 80));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        for (;;) {
            selector.select(100);
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                handler(key);
            }
        }
    }

    private static void handler(SelectionKey key) throws IOException {
        if (!key.isValid())
            return;

        SocketChannel channel = (SocketChannel) key.channel();
        if (key.isConnectable()) {
            if (channel.finishConnect()) {
                System.out.println("finishConnect ....");
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                channel.configureBlocking(false);

                ByteBuffer request = getRequestHead();
                channel.write(request);
            } else {
                channel.close();
            }
        } else if (key.isReadable()) {
            System.out.println("read data...");

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int length = channel.read(byteBuffer);
            if (length > 0) {
                byteBuffer.flip();  // write -> read
                byte[] readByte = new byte[byteBuffer.remaining()];
                byteBuffer.get(readByte);
                receiverBytes.add(readByte);

                if(length < 1024) {
                    long tl = receiverBytes.stream().mapToInt((byte[] bs) -> bs.length).sum();
                    byte[] tBytes = new byte[(int) tl];
                    int index = 0;
                    for(byte[] bs : receiverBytes) {
                        for(byte b : bs) {
                            tBytes[index++] = b;
                        }
                    }

//                    String str = new String(tBytes, Charset.forName("utf-8"));
//                    System.out.println(str);
                    byte[] body = stripHead(tBytes);
                    System.out.println(GzipUtils.uncompress(body));
                }

            } else {
                key.cancel();
                channel.close();
            }
        }
    }

    private static byte[] stripHead(byte[] readByte) {
        byte[] line = new byte[1024];
        int k = 0, headLastIndex = 0;
        for(int i = 0, j = readByte.length; i < j; i++) {
            if(readByte[i] == HttpConstants.CR && readByte[i+1] == HttpConstants.LF) {
                if(k == 0) {
                    // read empty line
                    headLastIndex = ++i;
                    break;
                }

                System.out.println(new String(line));
                i++;
                k = 0;
                line = new byte[1024];
            } else {
                line[k++] = readByte[i];
            }
        }

        // 这6个字节不清楚是什么字段
        byte[] lengthByte = new byte[6];
        for(int i = 0; i < lengthByte.length; i++) {
            lengthByte[i] = readByte[++headLastIndex];
        }
        System.out.println(new String(lengthByte));

//        headLastIndex = headLastIndex + 6; // 跳过6个字节

        // gzip 压缩后的 body
        byte[] body = new byte[readByte.length - headLastIndex - 1];
        for(int t = 0; t < body.length; t++) {
            body[t] = readByte[++headLastIndex];
        }
        return body;
    }


    private static ByteBuffer getRequestHead() throws IOException {
        String crlf = "\r\n";
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(HttpProxy.class.getResourceAsStream("/request_header.txt")))
        ) {
            String headers = reader.lines().collect(Collectors.joining(crlf));
            headers = headers + crlf;
            System.out.println(headers);
            return ByteBuffer.wrap(headers.getBytes(CharsetUtil.UTF_8));
        }
    }

}
