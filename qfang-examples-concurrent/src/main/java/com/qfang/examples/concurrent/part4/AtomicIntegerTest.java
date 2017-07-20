package com.qfang.examples.concurrent.part4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class AtomicIntegerTest {
	
	/**
	 * 主要接口
	 * 
	 * {@link AtomicInteger#get()}  获取当前值
	 * {@link AtomicInteger#getAndIncrement()}  i++
	 * {@link AtomicInteger#getAndDecrement()}  i--
	 * {@link AtomicInteger#incrementAndGet()}  ++i
	 * {@link AtomicInteger#decrementAndGet()}  --i
	 * {@link AtomicInteger#addAndGet(int delta)}   加指定的数，然后返回加之后的结果
	 * {@link AtomicInteger#getAndAdd(int)}
	 * {@link AtomicInteger#compareAndSet(int expect, int update)}
	 * 
	 */
	
	private static AtomicInteger intVal = new AtomicInteger(0);
//	private static int intVal = 0;
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new IncrementThread(), "t1");
		Thread t2 = new Thread(new IncrementThread(), "t2");
		Thread t3 = new Thread(new IncrementThread(), "t3");
		
		t1.start();
		t2.start();
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
		
		System.out.println(intVal.get());
//		System.out.println(intVal);
	}

	private static class IncrementThread implements Runnable {
		
		public void run() {
			for(int i = 0; i < 100; i++) {
				intVal.getAndIncrement();
			}
		}
		
	}
	
	
}
