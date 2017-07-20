package com.qfang.examples.concurrent.part6.mock;

import java.util.concurrent.Callable;

import com.qfang.examples.concurrent.common.ThreadUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class RealData<V> implements Data<V>, Callable<V> {
	
	private final String head;
	private final String tail;
	
	private V result;
	private boolean isComplete;
	
	public RealData(String head, String tail) {
		this.head = head;
		this.tail = tail;
	}
	
	@Override
	public synchronized V get() {
		while (!isComplete) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized V call() {
		ThreadUtils.sleepSilently(2000);
		result = (V) new StringBuilder().append(head).append(tail).toString();
		isComplete = true;
		notifyAll();
		return result;
	}

	
}
