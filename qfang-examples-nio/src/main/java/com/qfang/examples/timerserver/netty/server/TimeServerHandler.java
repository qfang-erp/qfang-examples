package com.qfang.examples.timerserver.netty.server;

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
public class TimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		String request = new String(bytes, CharsetUtil.UTF_8);
		System.out.println("The time server receive request : " + request);
		
		ByteBuf responseBuf = Unpooled.copiedBuffer(Config.responseBytes());
		ctx.write(responseBuf);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}
