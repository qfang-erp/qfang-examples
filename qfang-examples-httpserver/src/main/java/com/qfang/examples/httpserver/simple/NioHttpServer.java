package com.qfang.examples.httpserver.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import com.qfang.examples.httpserver.common.Constants;
import com.qfang.examples.httpserver.server.SimpleHttpServer;

/**
 * 基于NIO的实现
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public class NioHttpServer extends SimpleHttpServer {

	private Selector selector = null;
	private ServerSocketChannel channel = null;
	
	@Override
	public void start() throws IOException {
		selector = Selector.open();
		channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress(Constants.SERVER_PORT), 1024);
		channel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true) {
			selector.select(100);
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> it = keys.iterator();
			SelectionKey key = null;
			while(it.hasNext()) {
				key = it.next();
				it.remove();
				
				handleInner(key);
			}
		}
	}
	
	private void handleInner(SelectionKey key) throws IOException {
		if(!key.isValid())
			return ;
		
		if(key.isAcceptable()) {
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			SocketChannel sc = ssc.accept();
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ);
		} else if(key.isReadable()) {
			SocketChannel sc = (SocketChannel) key.channel();
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			int readBytes = sc.read(readBuffer);
			if(readBytes > 0) {
				readBuffer.flip();
				byte[] bytes = new byte[readBuffer.remaining()];
				readBuffer.get(bytes);
				String requestStr = new String(bytes, Charset.forName("UTF-8"));
				System.out.println(requestStr);


//				SimpleResponse response = this.doService(new SimpleRequest(requestStr));
//				this.doWrite(sc, response.getResponseBytes());
			} else if(readBytes < 0) {
				key.cancel();
				sc.close();
			} else {
				// = 0 ignore
			}
		}
	}
	
	private void doWrite(SocketChannel channel, byte[] response) throws IOException {
		ByteBuffer writeBuffer = ByteBuffer.allocate(response.length);
		writeBuffer.put(response);
		writeBuffer.flip();
		channel.write(writeBuffer);
	}
	
}
