package com.qfang.examples.disruptor.simpledemo.event;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月16日
 * @since 1.0
 */
public interface EventPublisher {
	
	void publish(String message) throws Exception;

}
