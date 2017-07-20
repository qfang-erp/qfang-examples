package com.qfang.examples.concurrent.part3.cacheline;

import java.util.stream.IntStream;

/**
 * 数组的缓存友好性测试示例
 * 同样的一个二维 byte 数组，在采用行优先和列优先模式访问时性能的差距
 * 行优先模式时由于 cpu cache 缓存行的原因，一个 cache line 会加载数组中多个数据到缓存中，所以访问更快
 * 而采用列优先模式时，每次访问时都需要重新去刷新 cache line，所以效率差很多
 * 
 * 测试结果：
 * 行优先时 46ms
 * 列优先时 1318ms
 * 
 * @author liaozhicheng
 * @date 2016年10月11日
 * @since 1.0
 */
public class ArrayCacheFriendlyTest {

	private static final int LENGTH = 10000;
	private static byte[][] array = new byte[LENGTH][LENGTH];
	
	public static void main(String[] args) {
		fillArray();

		long begin = System.currentTimeMillis();
		System.out.println(sumColumnFirst());
		System.out.printf("elapsed time: %s ms", System.currentTimeMillis() - begin);
	}
	
	private static void fillArray() {
		IntStream.range(0, LENGTH).forEach(i -> {
			IntStream.range(0, LENGTH).forEach(j -> {
				array[i][j] = 1;
			});
		});
	}
	
	public static long sumRowFirst() {
		long sum = 0;
		for(int i = 0; i < LENGTH; i++) {
			for(int j = 0; j < LENGTH; j++) {
				sum += array[i][j];  // 行优先
			}
		}
		return sum;
	}
	
	public static long sumColumnFirst() {
		long sum = 0;
		for(int i = 0; i < LENGTH; i++) {
			for(int j = 0; j < LENGTH; j++) {
				sum += array[j][i];  // 列优先
			}
		}
		return sum;
	}
	
}
