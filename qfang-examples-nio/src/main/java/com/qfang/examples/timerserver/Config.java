package com.qfang.examples.timerserver;

import io.netty.util.CharsetUtil;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月3日
 * @since 1.0
 */
public class Config {
	
	public static final String HOST = "127.0.0.1";

	public static final int PORT = 6666;
	
	public static final String REQUEST = "what's the time";
	
	public static byte[] requestBytes() {
		String request = "what's the time, client id : " + Thread.currentThread().getName();
		return request.getBytes(CharsetUtil.UTF_8);
	}
	
	public static byte[] responseBytes() {
		String response = "current time : " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
		return response.getBytes(CharsetUtil.UTF_8);
	}
	
}
