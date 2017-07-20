package com.qfang.examples.timerserver.nio.client;

import com.qfang.examples.timerserver.Config;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
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
public class TimeClientHandler implements Runnable {

	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;
	
	public TimeClientHandler() {
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			doConnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if(key != null) {
							key.cancel();
							if(key.channel() != null) {
								key.channel().close();
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private void doConnect() throws IOException {
		if(socketChannel.connect(new InetSocketAddress(Config.HOST, Config.PORT))) {
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		} else {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}

	private void doWrite(SocketChannel sc) throws IOException {
		byte[] request = Config.requestBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(request.length);
		writeBuffer.put(request);
		writeBuffer.flip();
		sc.write(writeBuffer);
		if(!writeBuffer.hasRemaining()) {
			System.out.println("send request 2 server success.");
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException {
		if(key.isValid()) {
			SocketChannel sc = (SocketChannel) key.channel();
			if(key.isConnectable()) {
				if(sc.finishConnect()) {
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				} else {
					// 连接失败，进程退出
					System.exit(1);
				}
			}
			
			if(key.isReadable()) {
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String response = new String(bytes, CharsetUtil.UTF_8);
					System.out.println(response);
					this.stop = true;
				} else if (readBytes < 0) {
					key.cancel();
					sc.close();
				} else {
					// ;
				}
			}
		}
	}
	
}
