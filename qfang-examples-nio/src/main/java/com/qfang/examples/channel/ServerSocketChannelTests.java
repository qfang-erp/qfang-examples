package com.qfang.examples.channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author: liaozhicheng
 * @date: 2018-05-02
 * @since: 1.0
 */
public class ServerSocketChannelTests {

    private static final String CRLF = "\r\n";

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup boosGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(5, 5, 5));
                        ch.pipeline().addLast(new LineBasedFrameDecoder(65536));
                        ch.pipeline().addLast(new SimpleHandler());
                    }
                });

        ChannelFuture future = serverBootstrap.bind(8787).sync();
        future.channel().closeFuture().sync();

        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    static class SimpleHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("receive new connect ..");
            ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(("welcome .." + CRLF).getBytes()));
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            String receiveMsg = new String(msg.array());

            ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(("response: " + receiveMsg + CRLF).getBytes()));
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if(evt == IdleStateEvent.READER_IDLE_STATE_EVENT) {
                // 处理超时链接，关闭客户端链接
                ctx.channel().close();
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }


}
