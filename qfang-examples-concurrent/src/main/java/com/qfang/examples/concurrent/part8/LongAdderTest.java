package com.qfang.examples.concurrent.part8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class LongAdderTest {
	
	private static final long TARGET_COUNT = 10000000;
	private static final int MAX_THREADS = 3;
	
	
	public static void main(String[] args) throws InterruptedException {
		syncLongAdd();
		atomicLongAdd();
		longAdderAdd();
	}
	
	
	private static void longAdderAdd() throws InterruptedException {
		LongAdder la = new LongAdder();
		CountDownLatch latch = new CountDownLatch(MAX_THREADS);
		
		long begin = System.currentTimeMillis();
		for(int i = 0; i < MAX_THREADS; i++) {
			new Thread(new LongAdderIncTask(la, latch)).start();
		}
		latch.await();
		long end = System.currentTimeMillis();
		System.out.println("longAdderAdd: count value : " + la.longValue() + ", elapsedTime : " + (end - begin));
	}
	
	
	private static class LongAdderIncTask implements Runnable {
		
		private final LongAdder la;
		private final CountDownLatch latch;
		
		LongAdderIncTask(LongAdder la, CountDownLatch latch) {
			this.la = la;
			this.latch = latch;
		}

		@Override
		public void run() {
			while (la.longValue() < TARGET_COUNT) {
				la.increment();
			}
			latch.countDown();
		}
		
	}
	
	
	
	///////////// ----------------
	
	private static void atomicLongAdd() throws InterruptedException {
		AtomicLong al = new AtomicLong();
		CountDownLatch latch = new CountDownLatch(MAX_THREADS);
		
		long begin = System.currentTimeMillis();
		for(int i = 0; i < MAX_THREADS; i++) {
			new Thread(new AtomicLongIncTask(al, latch)).start();
		}
		latch.await();
		long end = System.currentTimeMillis();
		System.out.println("atomicLongAdd: count value : " + al.get() + ", elapsedTime : " + (end - begin));
	}
	
	private static class AtomicLongIncTask implements Runnable {
		
		private final AtomicLong al;
		private final CountDownLatch latch;
		
		AtomicLongIncTask(AtomicLong al, CountDownLatch latch) {
			this.al = al;
			this.latch = latch;
		}

		@Override
		public void run() {
			while(al.get() < TARGET_COUNT) {
				al.incrementAndGet();
			}
			latch.countDown();
		}
		
	}
	
	
	////// -------------- 
	
	private static void syncLongAdd() throws InterruptedException {
		SyncLong sl = new SyncLong();
		CountDownLatch latch = new CountDownLatch(MAX_THREADS);
		
		long begin = System.currentTimeMillis();
		for(int i = 0; i < MAX_THREADS; i++) {
			new Thread(new SyncLongIncTask(sl, latch)).start();
		}
		latch.await();
		long end = System.currentTimeMillis();
		System.out.println("syncLongAdd: count value : " + sl.getCount() + ", elapsedTime : " + (end - begin));
	}
	
	
	private static class SyncLongIncTask implements Runnable {

		private final SyncLong sl;
		private final CountDownLatch latch;
		
		SyncLongIncTask(SyncLong sl, CountDownLatch latch) {
			this.sl = sl;
			this.latch = latch;
		}
		
		@Override
		public void run() {
			while (sl.getCount() < TARGET_COUNT) {
				sl.inc();
			}
			latch.countDown();
		}
		
	}
	

	private static class SyncLong {
		private volatile long count = 0l;
		
		public synchronized void inc() {
			count = count + 1;
		}
		public synchronized long getCount() {
			return count;
		}
	}
	
	
}
