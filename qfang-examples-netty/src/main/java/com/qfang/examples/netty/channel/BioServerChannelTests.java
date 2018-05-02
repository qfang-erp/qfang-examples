package com.qfang.examples.netty.channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-05-01
 * @since: 1.0
 */
public class BioServerChannelTests {

    final static String CRLF = "\r\n";


    public static void main(String[] args) {
        EventLoopGroup bossGroup = new OioEventLoopGroup();
        EventLoopGroup workerGroup = new OioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(OioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(65535));
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                @Override
                                public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                    ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(("welcome.." + CRLF).getBytes()));
                                }

                                @Override
                                protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                    System.out.println("receive msg...");

                                    String receiveMsg = new String(msg.array());
                                    System.out.println(receiveMsg);

                                    if("quit".equalsIgnoreCase(receiveMsg)) {
                                        ctx.pipeline().writeAndFlush(Unpooled.wrappedBuffer(("ByBy .. " + CRLF).getBytes()));
                                        ctx.channel().close().sync();
                                    } else{
                                        ctx.pipeline().writeAndFlush(Unpooled.wrappedBuffer(("response: " + receiveMsg + CRLF).getBytes()));
                                    }
                                }
                            });
                        }
                    });

            ChannelFuture future = bootstrap.bind(8866).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
