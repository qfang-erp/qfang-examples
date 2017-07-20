package com.qfang.examples.disruptor.simpledemo.event;

import java.util.concurrent.Executors;

import com.qfang.examples.disruptor.simpledemo.event.handler.HelloEventHandler;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月8日
 * @since 1.0
 */
public class Main {

	public static void main(String[] args) {
		EventFactory<HelloEvent> eventFactory = new HelloEventFactory();
		int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

		Disruptor<HelloEvent> disruptor = new Disruptor<HelloEvent>(
				eventFactory, ringBufferSize, Executors.defaultThreadFactory(),
				ProducerType.SINGLE, new YieldingWaitStrategy());

		EventHandler<HelloEvent> eventHandler = new HelloEventHandler();
		disruptor.handleEventsWith(eventHandler, eventHandler);

		disruptor.start();

	}

}
