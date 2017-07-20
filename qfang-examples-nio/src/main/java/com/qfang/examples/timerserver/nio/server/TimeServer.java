package com.qfang.examples.timerserver.nio.server;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月17日
 * @since 1.0
 */
public class TimeServer {

	public static void main(String[] args) {
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer();
		new Thread(timeServer).start();
	}
	
}
