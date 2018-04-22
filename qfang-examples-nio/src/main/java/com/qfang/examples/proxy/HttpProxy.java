package com.qfang.examples.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;


/**
 * @author: liaozhicheng
 * @date: 2018-04-19
 * @since: 1.0
 */
public class HttpProxy {

    public static void main(String[] args) throws InterruptedException, IOException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpResponseDecoder());
                        ch.pipeline().addLast(new ClientChannelHandler());
                    }
                });

        ChannelFuture future = bootstrap.connect("47.92.66.207", 80);
        future.addListener((ChannelFuture future1) -> {
            if(future1.isSuccess()) {
                System.out.println("connect success");

//                ByteBuf buf = Unpooled.wrappedBuffer("xx".getBytes());
                future1.channel().pipeline().writeAndFlush(getRequestHead()).sync();
            } else {
                System.out.println("error ..");
            }
        });

    }

    private static class ClientChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

        CompositeByteBuf compositeByteBuf;

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            System.out.println("messageReceived....");

            ByteBufAllocator allocator = ctx.channel().alloc();
            if(compositeByteBuf == null)
                compositeByteBuf = allocator.compositeDirectBuffer();

            // addComponent 参数一定要使用 Unpooled.copiedBuffer copy 后的 ByteBuf，这里是尝试将多出收到的消息缓存起来，最后再进行一次统一的解码
            // messageReceived 方法被调用完成之后，都会将 msg release 掉，导致最后解码时发现之前所有读取到的数据都已经不能用了 refCnt: 0
            // addComponent 方法不会维护 writerIndex，需要自己来手动处理
            compositeByteBuf.addComponent(Unpooled.copiedBuffer(msg)).writerIndex(compositeByteBuf.writerIndex() + msg.writerIndex());
            int dl = msg.readableBytes();
            System.out.println(dl);
            if(dl < 1024) {
                System.out.println(compositeByteBuf.readableBytes());

                byte[] bytes = new byte[compositeByteBuf.capacity()];
                compositeByteBuf.getBytes(compositeByteBuf.readerIndex(), bytes);
                System.out.println(new String(bytes, Charset.forName("UTF-8")));
            }
        }
    }


    private static ByteBuf getRequestHead() throws IOException {
        String crlf = "\r\n";
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(HttpProxy.class.getResourceAsStream("/request_header.txt")))
        ) {
            String headers = reader.lines().collect(Collectors.joining(crlf));
            headers = headers + crlf + crlf;
            System.out.println(headers);
           return Unpooled.wrappedBuffer(headers.getBytes());
        }
    }


}
