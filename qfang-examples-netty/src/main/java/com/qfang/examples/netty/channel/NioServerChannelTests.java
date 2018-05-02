package com.qfang.examples.netty.channel;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-05-01
 * @since: 1.0
 */
public class NioServerChannelTests {

    private static final String CRLF = "\r\n";


    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        EventLoopGroup workerGroup = new NioEventLoopGroup(20);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(65536));

                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {

                                @Override
                                public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("channelRegistered: " + ctx.channel().remoteAddress().toString());
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(("welcome .." + CRLF).getBytes()));
                                }

                                @Override
                                protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) {
                                    String receiveMsg = new String(msg.array());
                                    System.out.println(receiveMsg);

                                    ctx.pipeline().writeAndFlush(Unpooled.wrappedBuffer(("response: " + receiveMsg + CRLF).getBytes()));
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    cause.printStackTrace();
                                    ctx.channel().close();
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.bind(8787).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
