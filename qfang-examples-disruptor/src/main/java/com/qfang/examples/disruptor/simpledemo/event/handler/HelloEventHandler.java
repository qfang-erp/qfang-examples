package com.qfang.examples.disruptor.simpledemo.event.handler;

import com.qfang.examples.disruptor.simpledemo.event.HelloEvent;
import com.lmax.disruptor.EventHandler;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月8日
 * @since 1.0
 */
public class HelloEventHandler implements EventHandler<HelloEvent> {

	@Override
	public void onEvent(HelloEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		System.out.println("receive message : " + event.getMessage());
	}

}
