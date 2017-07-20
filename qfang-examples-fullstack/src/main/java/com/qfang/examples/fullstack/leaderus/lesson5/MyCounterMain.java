package com.qfang.examples.fullstack.leaderus.lesson5;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import com.qfang.examples.fullstack.leaderus.lesson5.AtomicLongArrayCounter.IndexHolder;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月8日
 * @since 1.0
 */
public class MyCounterMain {
	
	public static void main(String[] args) {
		final int threadCount = 80, executeCount = 20;
		
		double elapsedTimeAvg = IntStream.range(0, executeCount).mapToLong(x -> {
			return execute(threadCount);
		}).average().getAsDouble();
		
		System.out.format("thread count : %s, execute count : %s, elapsed time average : %s ms \n", 
				threadCount, executeCount, elapsedTimeAvg);
	}
	
	private static long execute(int threadCount) {
		final MyCounter counter = new AtomicLongArrayCounter(threadCount);
		
		long start = System.currentTimeMillis();
		
		List<Thread> ts = IntStream.range(0, threadCount)
			.mapToObj(index -> {
				return new Thread(new CounterJob(counter, index));
			})
			.peek(Thread::start)
			.collect(toList());
		
		ts.forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.format("thread count : %s, now count value : %s, elapsed time : %s \n", 
				threadCount, counter.getCurValue(), elapsedTime);
		return elapsedTime;
	}
	
	private static class CounterJob implements Runnable {

		private final MyCounter counter;
		private final int index;
		
		CounterJob(MyCounter counter, int index) {
			this.counter = counter;
			this.index = index;
		}
		
		@Override
		public void run() {
			if(counter instanceof AtomicLongArrayCounter 
					|| counter instanceof LongArrayCounter) {
				new IndexHolder(index);
			}
			
			IntStream.range(0, 1000000).forEach(x -> counter.incr());
		}
		
	}

}
