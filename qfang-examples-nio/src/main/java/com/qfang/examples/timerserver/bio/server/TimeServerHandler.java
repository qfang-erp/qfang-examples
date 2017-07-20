package com.qfang.examples.timerserver.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月2日
 * @since 1.0
 */
public class TimeServerHandler implements Runnable {
	
	private final Socket socket;
	
	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			writer = new PrintWriter(this.socket.getOutputStream(), true);
			while (true) {
				String request = reader.readLine();
				if(request == null)
					break;
				System.out.println("recive request : " + request);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
				String currentTime = format.format(new Date());
				String response = "current time is : " + currentTime;
				writer.println(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(writer);
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
