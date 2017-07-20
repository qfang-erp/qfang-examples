package com.qfang.examples.concurrent.part3.cacheline;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 多个线程更新同一个/不同 cache line 数据时的性能差距
 * 同一个 cache line ： 5829ms
 * 不同 cache line ： 2465ms （大概相差一倍的性能）
 * 
 * @author liaozhicheng
 * @date 2016年10月19日
 * @since 1.0
 */
public class CacheLineTest {

	private static int[] arrays = new int[200];
	
	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		
		List<Thread> tl = IntStream.range(0, 8).mapToObj(x -> {
			return new Thread(new UpdateArrayIndex(x * 16));  // *1 或者 *16 (64byte的cache line正好是16个int)
		}).peek(Thread::start).collect(toList());
		
		tl.stream().forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.out.printf("elapsed time: %s ms ", System.currentTimeMillis() - begin);
//		System.out.println(Arrays.toString(arrays));
	}
	
	
	private static class UpdateArrayIndex implements Runnable {

		private final int index;
		private final Random random;
		private UpdateArrayIndex(int index) {
			this.index = index;
			this.random = new Random();
		}
		
		@Override
		public void run() {
			for(int i = 0; i < 50000000; i++) {
				arrays[index] = random.nextInt(10);
			}
		}
		
	}
	
	
}
