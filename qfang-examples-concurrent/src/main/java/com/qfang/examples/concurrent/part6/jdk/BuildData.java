package com.qfang.examples.concurrent.part6.jdk;

import java.util.concurrent.Callable;

import com.qfang.examples.concurrent.common.ThreadUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class BuildData implements Callable<String> {
	
	private final String head;
	private final String tail;
	
	public BuildData(String head, String tail) {
		this.head = head;
		this.tail = tail;
	}
	
	
	@Override
	public String call() throws Exception {
		ThreadUtils.sleepSilently(1000);
		return new StringBuilder().append(head).append(tail).toString();
	}
	
}
