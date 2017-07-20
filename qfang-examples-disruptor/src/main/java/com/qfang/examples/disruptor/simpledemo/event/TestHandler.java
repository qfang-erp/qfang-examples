package com.qfang.examples.disruptor.simpledemo.event;

import com.qfang.examples.disruptor.utils.TimeTracer;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月16日
 * @since 1.0
 */
public class TestHandler {
	private TimeTracer tracer;

	public TestHandler(TimeTracer tracer) {
		this.tracer = tracer;
	}

	/**
	 * 如果返回 true，则表示处理已经全部完成，不再处理后续事件；
	 * 
	 * @param event
	 * @return
	 */
	public boolean process(HelloEvent event) {
		tracer.count();
		return false;
	}
}
