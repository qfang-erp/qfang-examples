package com.qfang.examples.concurrent.common;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ThreadUtils {
	
	
	public static void sleepSilently(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	public static String name() {
		return Thread.currentThread().getName();
	}
	
}
