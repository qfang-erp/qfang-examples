package com.qfang.examples.concurrent.part4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class AtomicIntegerArrayTest {
	
	/**
	 * 类似 {@link AtomicInteger} 的 API
	 * {@link AtomicIntegerArray#incrementAndGet(int)}  对数组指定偏移量上的元素自增并返回自增后的结果
	 */
	
	private static AtomicIntegerArray intArr = new AtomicIntegerArray(3);
	
	public static void main(String[] args) throws InterruptedException {
		// 2个线程对数组的第0位数字进行自增
		Thread t01 = new Thread(new IncrementThread(0), "t01");
		Thread t02 = new Thread(new IncrementThread(0), "t02");
		
		Thread t11 = new Thread(new IncrementThread(1), "t11");
		Thread t12 = new Thread(new IncrementThread(1), "t12");
		Thread t13 = new Thread(new IncrementThread(1), "t13");
		
		Thread t21 = new Thread(new IncrementThread(2), "t21");
		Thread t22 = new Thread(new IncrementThread(2), "t22");
		
		t01.start();
		t02.start();
		t11.start();
		t12.start();
		t13.start();
		t21.start();
		t22.start();
		
		t01.join();
		t02.join();
		t11.join();
		t12.join();
		t13.join();
		t21.join();
		t22.join();
		
		System.out.println(intArr);
	}
	
	
	
	static class IncrementThread implements Runnable {

		private final int offset;
		
		IncrementThread(int offset) {
			this.offset = offset;
		}
		
		
		public void run() {
			for(int i = 0; i < 1000; i++) {
				intArr.incrementAndGet(offset);
			}
		}
		
	}

}
