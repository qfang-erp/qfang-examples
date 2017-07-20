package com.qfang.examples.timerserver.nio.client;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月17日
 * @since 1.0
 */
public class TimeClient {

	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			new Thread(new TimeClientHandler()).start();
		}
	}
	
}
