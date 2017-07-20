package com.qfang.examples.timerserver.netty.client;

import com.qfang.examples.timerserver.Config;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月31日
 * @since 1.0
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf requestBuf = Unpooled.copiedBuffer(Config.requestBytes());
		ctx.writeAndFlush(requestBuf);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf responseBuf = (ByteBuf) msg;
		byte[] bytes = new byte[responseBuf.readableBytes()];
		responseBuf.readBytes(bytes);
		String response = new String(bytes, CharsetUtil.UTF_8);
		System.out.println(response);
		
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
