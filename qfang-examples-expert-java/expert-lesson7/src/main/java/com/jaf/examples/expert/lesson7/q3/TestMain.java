package com.jaf.examples.expert.lesson7.q3;

import java.util.Random;
import java.util.stream.IntStream;

import net.bramp.unsafe.UnsafeArrayList;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年11月30日
 * @since 1.0
 */
public class TestMain {
	
	private static final int COUNT = 1000000;
	
	public static void main(String[] args) {
		Random random = new Random();
		
		UnsafeArrayList<MycatRecord> records = new UnsafeArrayList<>(MycatRecord.class, COUNT);
		IntStream.range(0, COUNT).forEach(i -> records.add(new MycatRecord(random.nextInt(), random.nextInt(), (short) random.nextInt())));
		
		long start = System.currentTimeMillis();
		records.stream()
			.sorted((r1, r2) -> Integer.compare(r2.col1, r1.col1)).limit(1000)
			.count();
//			.forEach(System.out::println);
		
		long elaspedTime = System.currentTimeMillis() - start;
		System.out.println("elasped time : " + elaspedTime);
		
	}

}
