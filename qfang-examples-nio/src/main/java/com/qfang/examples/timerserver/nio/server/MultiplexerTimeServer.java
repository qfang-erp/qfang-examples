package com.qfang.examples.timerserver.nio.server;

import com.qfang.examples.timerserver.Config;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月17日
 * @since 1.0
 */
public class MultiplexerTimeServer implements Runnable {

	private Selector selector;
	
	private ServerSocketChannel channel;

	private volatile boolean stop;
	
	public MultiplexerTimeServer() {
		try {
			selector = Selector.open();
			channel = ServerSocketChannel.open();
			channel.configureBlocking(false);
			channel.socket().bind(new InetSocketAddress(Config.PORT), 1024);
			channel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("The time server is start in port : " + Config.PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		while(!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if(key != null) {
							key.cancel();
							if(key.channel() != null)
								key.channel().close();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException {
		if(key.isValid()) {
			if(key.isAcceptable()) {
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			if(key.isReadable()) {
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String request = new String(bytes, CharsetUtil.UTF_8);
					System.out.println("The time server receive request : " + request);
					
					doWrite(sc, Config.responseBytes());
				} else if(readBytes < 0) {
					key.cancel();
					sc.close();
				} else {
					// = 0 ignore
				}
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
