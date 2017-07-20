package com.qfang.examples.timerserver.bio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.qfang.examples.timerserver.Config;
import org.apache.commons.io.IOUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月3日
 * @since 1.0
 */
public class TimeClient {
	
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		try {
			socket = new Socket(Config.HOST, Config.PORT);
			reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
			writer = new PrintWriter(socket.getOutputStream(), true);
			String request = "what's the time";
			writer.println(request);
			System.out.println("client send a request : " + request);
			
			String response = reader.readLine();
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(reader);
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
