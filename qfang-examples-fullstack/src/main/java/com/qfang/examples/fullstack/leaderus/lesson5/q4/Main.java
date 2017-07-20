package com.qfang.examples.fullstack.leaderus.lesson5.q4;

import static java.util.stream.Collectors.toList;


import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


import org.apache.commons.lang3.RandomUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月8日
 * @since 1.0
 */
public class Main {
	
	public static void main(String[] args) {
		final AtomicByteArray byteArray = new AtomicByteArray(5);
		
		List<Thread> ts = IntStream.range(0, 20).mapToObj(x -> {
			return new Thread(x % 2 == 0 ? new AppendJob(byteArray) : new PopJob(byteArray));
		}).peek(Thread::start).collect(toList());
		
		ts.stream().forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(byteArray.getCurPos());
		System.out.println(Arrays.toString(byteArray.getArray()));
	}
	
	
	static class AppendJob implements Runnable {
		
		private final ByteArray byteArray;
		
		private AppendJob(ByteArray byteArray) {
			this.byteArray = byteArray;
		}

		@Override
		public void run() {
			IntStream.range(0, 20).forEach(x -> {
				byte b = RandomUtils.nextBytes(1)[0];
				byteArray.append(b);
			});
		}
		
	}
	
	static class PopJob implements Runnable {
		
		private final ByteArray byteArray;
		
		private PopJob(ByteArray byteArray) {
			this.byteArray = byteArray;
		}

		@Override
		public void run() {
			IntStream.range(0, 20).forEach(x -> {
				byteArray.poll();
			});
		}
		
	}
	
	
}
