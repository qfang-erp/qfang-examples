package com.qfang.examples.timerserver.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.qfang.examples.timerserver.Config;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月2日
 * @since 1.0
 */
public class TimeServer {
	
	
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(Config.PORT);
			System.out.println("The time server is start in port : " + Config.PORT);
			Socket socket = null;
			while (true) {
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(server != null) {
				System.out.println("The time server close");
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
