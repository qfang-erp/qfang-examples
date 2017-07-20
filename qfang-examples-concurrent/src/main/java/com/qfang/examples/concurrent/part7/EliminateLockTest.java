package com.qfang.examples.concurrent.part7;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class EliminateLockTest {
	
	private static final int COUNT = 20000000;
	
	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		for(int i = 0; i < COUNT; i++) {
			createStringBuffer("str1", "str2");
		}
		long end = System.currentTimeMillis();
		System.out.println("elapsedTime : " + (end - begin));
	}
	
	private static String createStringBuffer(String arg1, String arg2) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(arg1);  // 同步操作，该对象不可能共享，可以不同步
		buffer.append(arg2);
		return buffer.toString();
	}
	
}
