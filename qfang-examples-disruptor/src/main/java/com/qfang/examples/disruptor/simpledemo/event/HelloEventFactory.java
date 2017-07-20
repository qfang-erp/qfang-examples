package com.qfang.examples.disruptor.simpledemo.event;

import com.lmax.disruptor.EventFactory;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月8日
 * @since 1.0
 */
public class HelloEventFactory implements EventFactory<HelloEvent> {

	@Override
	public HelloEvent newInstance() {
		return new HelloEvent();
	}
	
}
