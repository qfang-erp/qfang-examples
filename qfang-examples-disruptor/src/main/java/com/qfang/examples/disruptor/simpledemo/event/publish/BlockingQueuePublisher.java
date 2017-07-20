package com.qfang.examples.disruptor.simpledemo.event.publish;

import java.util.concurrent.ArrayBlockingQueue;

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
public class BlockingQueuePublisher implements EventPublisher {

	private ArrayBlockingQueue<HelloEvent> queue;
	private TestHandler handler;

	public BlockingQueuePublisher(int maxEventSize, TestHandler handler) {
		this.queue = new ArrayBlockingQueue<HelloEvent>(maxEventSize);
		this.handler = handler;
	}

	public void start() {
		Thread thrd = new Thread(new Runnable() {
			@Override
			public void run() {
				handle();
			}
		});
		thrd.start();
	}

	private void handle() {
		try {
			HelloEvent evt;
			while (true) {
				evt = queue.take();
				if (evt != null && handler.process(evt)) {
					// 完成后自动结束处理线程；
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(String message) throws Exception {
		HelloEvent event = new HelloEvent();
		event.setMessage(message);
		queue.put(event);
	}

}
