package com.qfang.examples.disruptor.simpledemo.event.publish;

import com.qfang.examples.disruptor.simpledemo.event.EventPublisher;
import com.qfang.examples.disruptor.simpledemo.event.HelloEvent;
import com.qfang.examples.disruptor.simpledemo.event.TestHandler;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月16日
 * @since 1.0
 */
public class DirectingPublisher implements EventPublisher {

	private TestHandler handler;
	private HelloEvent event = new HelloEvent();

	public DirectingPublisher(TestHandler handler) {
		this.handler = handler;
	}

	@Override
	public void publish(String message) throws Exception {
		event.setMessage(message);
		handler.process(event);
	}

}
