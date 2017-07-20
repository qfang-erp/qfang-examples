package com.qfang.examples.concurrent.part8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.qfang.examples.concurrent.common.ThreadUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class CompletableFutureTest {
	
	public static void main(String[] args) throws Exception {
		test1();
//		test2();
	}
	
	
	static void test1() {
		final CompletableFuture<Integer> cf = new CompletableFuture<Integer>();
		new Thread(new Job(cf)).start();
		
		// 模拟长时间的计算结果
		ThreadUtils.sleepSilently(1000);
		
		// 告知完成结果
		cf.complete(10);
	}
	
	
	private static final class Job implements Runnable {

		private final CompletableFuture<Integer> cf;
		
		Job(CompletableFuture<Integer> cf) {
			this.cf = cf;
		}
		
		
		@Override
		public void run() {
			int result = 0;
			try {
				int cfVal = cf.get();
				result = cfVal * cfVal;
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}
	}

	/////////////--------------
	
	static void test2() throws InterruptedException, ExecutionException {
		final CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> calc(20)).thenAccept(System.out::println);
		future.get();
	}
	
	
	private static int calc(int para) {
		ThreadUtils.sleepSilently(2000);
		return para * para;
	}
	
	
}
