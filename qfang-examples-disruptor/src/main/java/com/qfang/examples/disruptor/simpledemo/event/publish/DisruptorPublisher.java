package com.qfang.examples.disruptor.simpledemo.event.publish;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.qfang.examples.disruptor.simpledemo.event.EventPublisher;
import com.qfang.examples.disruptor.simpledemo.event.HelloEvent;
import com.qfang.examples.disruptor.simpledemo.event.HelloEventFactory;
import com.qfang.examples.disruptor.simpledemo.event.TestHandler;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月16日
 * @since 1.0
 */
public class DisruptorPublisher implements EventPublisher {

    private class HelloEventHandler implements EventHandler<HelloEvent> {

        private TestHandler handler;

        public HelloEventHandler(TestHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onEvent(HelloEvent event, long sequence, boolean endOfBatch)
                throws Exception {
            handler.process(event);
        }

    }
    
    private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();

    private Disruptor<HelloEvent> disruptor;
    private HelloEventHandler handler;
    private RingBuffer<HelloEvent> ringbuffer;    
    private ExecutorService executor;

    public DisruptorPublisher(int bufferSize, TestHandler handler) {
        this.handler = new HelloEventHandler(handler);
        
        EventFactory<HelloEvent> eventFactory = new HelloEventFactory();
		int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；
        
        executor = Executors.newSingleThreadExecutor();
        disruptor = new Disruptor<HelloEvent>(
				eventFactory, ringBufferSize, Executors.defaultThreadFactory(),
				ProducerType.SINGLE, YIELDING_WAIT);
    }

    @SuppressWarnings("unchecked")
    public void start() {
        disruptor.handleEventsWith(handler);
        disruptor.start();
        ringbuffer = disruptor.getRingBuffer();
    }

    @Override
    public void publish(String message) throws Exception {
        long seq = ringbuffer.next();
        try {
            HelloEvent evt = ringbuffer.get(seq);
            evt.setMessage(message);
        } finally {
            ringbuffer.publish(seq);
        }
    }
	
}
