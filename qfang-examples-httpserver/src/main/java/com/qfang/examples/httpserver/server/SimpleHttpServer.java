package com.qfang.examples.httpserver.server;

import com.qfang.examples.httpserver.HttpServer;
import com.qfang.examples.httpserver.Response;
import com.qfang.examples.httpserver.common.Constants;
import com.qfang.examples.httpserver.simple.SimpleRequest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.qfang.examples.httpserver.common.Constants.SERVER_PORT;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月22日
 * @since 1.0
 */
public class SimpleHttpServer extends HttpServer {
	
	@Override
	public void start() throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("******* open  " + socket.toString() + " connected. *******");

				try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(socket.getInputStream()))) {
					String lineInput;
					StringBuilder requestStr = null;
					while((lineInput = reader.readLine()) != null) {
						System.out.println(lineInput);
						if(lineInput.matches(Constants.REQUEST_HEAD_FIRST_LINE_PATTERN)) {
							requestStr = new StringBuilder();
						}
						requestStr.append(lineInput).append(Constants.SPLIT);

						if(lineInput.isEmpty()) {
							Response response = this.handlerRequest(new SimpleRequest(requestStr.toString()));
							this.doWrite(socket.getOutputStream(), response.toBytes());
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doWrite(OutputStream writer, byte[] responseBytes) throws IOException {
		writer.write(responseBytes);
		writer.flush();
	}

}
