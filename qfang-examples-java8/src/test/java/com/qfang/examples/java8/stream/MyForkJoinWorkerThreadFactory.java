package com.qfang.examples.java8.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.stream.IntStream;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年12月26日
 * @since 1.0
 */
public class MyForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory {
	
	static {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.threadFactory", "MyForkJoinWorkerThreadFactory");
	}
	
	public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
		Context.set("zhangsan");
		return new MyForkJoinWorkerThread(pool);
	}

	
	class MyForkJoinWorkerThread extends ForkJoinWorkerThread {

		protected MyForkJoinWorkerThread(ForkJoinPool pool) {
			super(pool);
		}
		
	}
	
	static class Context {
		
		private static ThreadLocal<String> username = new ThreadLocal<>();
		
		public static void set(String name) {
			username.set(name);
		}
		
		public static String get() {
			return username.get();
		}
		
	}
	
	public static void main(String[] args) {
		IntStream.range(0, 5).parallel().forEach(i -> {
			System.out.println(Context.get());
		});
	}
	
}
