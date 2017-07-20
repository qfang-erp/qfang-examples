package com.qfang.examples.timerserver.netty.client;

import java.net.InetSocketAddress;

import com.qfang.examples.timerserver.Config;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月31日
 * @since 1.0
 */
public class TimeClient {
	
	public void connect() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new TimeClientHandler());
					}
				});
			
			// 发起异步连接操作
			ChannelFuture f = b.connect(new InetSocketAddress(Config.HOST, Config.PORT)).sync();
			
			// 等待客户端链路关闭
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new TimeClient().connect();
	}

}
