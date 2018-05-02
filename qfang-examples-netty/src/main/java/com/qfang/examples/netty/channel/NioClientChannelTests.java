package com.qfang.examples.netty.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * 模拟 20 个客户端链接服务器，每个客户端发送 100 条消息后自动退出
 *
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-05-03
 * @since: 1.0
 */
public class NioClientChannelTests {

    private static final String CRLF = "\r\n";

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 20; i++) {
            doSend(i);
        }
    }


    static void doSend(int clientNo) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(65536));
                        ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));

                        ch.pipeline().addLast(new ClientSendHandler(clientNo));
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8787).sync();
        ChannelFuture closeFuture = channelFuture.channel().closeFuture();
        closeFuture.addListeners(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                future.channel().close();
                workerGroup.shutdownGracefully();
            }
        });
    }


    static class ClientSendHandler extends SimpleChannelInboundHandler<String> {

        private final int clientNo;
        private int sendIndex;

        ClientSendHandler(int clientNo) {
            this.clientNo = clientNo;
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println(msg);

            if(sendIndex < 1000) {
                ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(("clientId: " + clientNo + ", sendIndex: " + sendIndex + CRLF).getBytes()));
                sendIndex++;
            } else {
                ctx.channel().close();
            }
        }

    }

}
