package com.qfang.examples.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
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
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                        ch.pipeline().addLast(new HttpContentDecompressor());
                        ch.pipeline().addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
                        ch.pipeline().addLast(new ClientChannelHandler());
                    }
                });

        ChannelFuture future = bootstrap.connect("www.bootcss.com", 80);
        future.addListener((ChannelFuture future1) -> {
            if(future1.isSuccess()) {
                System.out.println("connect success");
                future1.channel().pipeline().writeAndFlush(getRequestHead());
            } else {
                System.out.println("error ..");
            }
        });

        future.channel().closeFuture().sync();
        group.shutdownGracefully();
    }

    private static class ClientChannelHandler extends SimpleChannelInboundHandler<HttpObject> {

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
            System.out.println("messageReceived....");

            System.out.println(msg);
            DefaultFullHttpResponse response = (DefaultFullHttpResponse) msg;

            System.out.println(response.content().toString(CharsetUtil.UTF_8));

            ctx.channel().close();
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
