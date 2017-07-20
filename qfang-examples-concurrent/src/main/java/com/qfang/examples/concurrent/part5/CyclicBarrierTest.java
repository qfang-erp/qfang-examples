package com.qfang.examples.concurrent.part5;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.qfang.examples.concurrent.common.ThreadUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class CyclicBarrierTest {
	
	private static final int TOTAL_THREAD = 5;
	private static Random random = new Random();
	
	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(TOTAL_THREAD, new Runnable() {
			// 会在所有线程都到达 barrier 临界点，并且唤醒其他线程之前执行
			public void run() {
				System.out.println("========================");
			}
		});
		
		for(int i = 0; i < TOTAL_THREAD; i++) {
			new Thread(new Job(cyclicBarrier, i), "job-" + i).start();
		}
	}

	
	private static class Job implements Runnable {

		private final CyclicBarrier cyclicBarrier;
		
		private Job(CyclicBarrier cyclicBarrier, int index) {
			this.cyclicBarrier = cyclicBarrier;
		}
		
		public void run() {
			// 循环工作两次，表明 CyclicBarrier 是可重用的
			for(int i = 0; i < 2; i++) {
				try {
					int r = random.nextInt(3000);
					ThreadUtils.sleepSilently(r);
					System.out.println(ThreadUtils.name() + " complete init task, elapsed time " + r);
					
					// 每个线程先工作随机长时间，用于完成初始化的工作，但是所有线程必须等待其他所有线程都完成初始化工作，才能进行后续操作
					cyclicBarrier.await();
					
					System.out.println(ThreadUtils.name() + " do other work ...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
