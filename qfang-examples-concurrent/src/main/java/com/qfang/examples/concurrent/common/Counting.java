package com.qfang.examples.concurrent.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class Counting {
	
	private final AtomicInteger count = new AtomicInteger(0);
	
	public void increment() {
		count.incrementAndGet();
	}
	
	public long getCount() {
		return count.get();
	}
	
}
