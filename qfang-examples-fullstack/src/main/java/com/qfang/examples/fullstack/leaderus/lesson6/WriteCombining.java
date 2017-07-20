package com.qfang.examples.fullstack.leaderus.lesson6;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月19日
 * @since 1.0
 */
public class WriteCombining {
	
	private static final int ITERATIONS = Integer.MAX_VALUE;
	private static final int ITEMS = 1 << 24;
	private static final int MASK = ITEMS - 1;

	private static final byte[] arrayA = new byte[ITEMS];
	private static final byte[] arrayB = new byte[ITEMS];
	private static final byte[] arrayC = new byte[ITEMS];
	private static final byte[] arrayD = new byte[ITEMS];
	private static final byte[] arrayE = new byte[ITEMS];
	private static final byte[] arrayF = new byte[ITEMS];

	public static void main(final String[] args) {
		for (int i = 1; i <= 3; i++) {
			System.out.println(i + " SingleLoop duration (ns) = " + runCaseOne());
			System.out.println(i + " SplitLoop duration (ns) = " + runCaseTwo());
		}

		int result = arrayA[1] + arrayB[2] + arrayC[3] + arrayD[4] + arrayE[5]
				+ arrayF[6];

		System.out.println("result = " + result);
	}

	public static long runCaseOne() {
		long start = System.nanoTime();
		int i = ITERATIONS;
		while (--i != 0) {
			int slot = i & MASK;
			byte b = (byte) i;
			arrayA[slot] = b;
			arrayB[slot] = b;
			arrayC[slot] = b;
			arrayD[slot] = b;
			arrayE[slot] = b;
			arrayF[slot] = b;
		}
		return System.nanoTime() - start;
	}

	public static long runCaseTwo() {
		long start = System.nanoTime();
		int i = ITERATIONS;
		while (--i != 0) {
			int slot = i & MASK;
			byte b = (byte) i;
			arrayA[slot] = b;
			arrayB[slot] = b;
			arrayC[slot] = b;
		}
		
		i = ITERATIONS;
		while (--i != 0) {
			int slot = i & MASK;
			byte b = (byte) i;
			arrayD[slot] = b;
			arrayE[slot] = b;
			arrayF[slot] = b;
		}
		
		return System.nanoTime() - start;
	}

}
