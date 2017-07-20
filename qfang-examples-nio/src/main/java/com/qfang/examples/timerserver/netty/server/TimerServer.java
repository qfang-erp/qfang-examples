package com.qfang.examples.timerserver.netty.server;


import com.qfang.examples.timerserver.Config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月31日
 * @since 1.0
 */
public class TimerServer {
	
	public void bind() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 配置服务器端NIO线程组
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChildChannelHandler());
			
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(Config.PORT).sync();
			
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel sc) throws Exception {
			sc.pipeline().addLast(new TimeServerHandler());
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new TimerServer().bind();
	}
	
}
