package com.qfang.examples.concurrent.part3;

import java.util.stream.IntStream;

/**
 * 指令重排的示例代码
 * 如果按照正常串行语义的执行，你可能觉得 execute 方法之后的输出重视 (x: 1, y: 1)，可是在多线程环境下并缺少同步的情况下却不总是这样
 * 最终的执行结果可能为 (x: 0, y: 0) / (x: 1, y: 0) / (x: 0, y: 1) / (x: 1, y: 1) 都有可能
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月16日
 * @since 1.0
 */
public class PossibleReordering {
	
	int x = 0, y = 0;
	int a = 0, b = 0;
	
	public static void main(String[] args) {
		IntStream.range(0, 1000).forEach(x -> {
			new PossibleReordering().execute();
		});
	}

	public void execute() {
		Thread one = new Thread(() -> {
			a = 1;
			x = b;
		});
		
		Thread other = new Thread(() -> {
			b = 1;
			y = a;
		});
		
		one.start();
		other.start();
		
		try {
			one.join();
			other.join();
		} catch (InterruptedException e) {
			// ignore
		}
		
		System.out.format("(x: %s, y: %s) \n", x, y);
	}
	
}
