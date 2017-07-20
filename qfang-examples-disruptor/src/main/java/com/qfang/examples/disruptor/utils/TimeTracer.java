package com.qfang.examples.disruptor.utils;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月12日
 * @since 1.0
 */
public interface TimeTracer {
	
	void start();
	
	void count();

	boolean isEnd();
	
	long getMilliTimeSpan();
	
	void waitForReached() throws InterruptedException;
	
}
