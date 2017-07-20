package com.qfang.examples.concurrent.part6.jdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class Client {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService excutor = Executors.newFixedThreadPool(1);
		Future<String> future = excutor.submit(new BuildData("hello", " world!"));
		
		// do other thing...
		
		System.out.println(future.get());
		excutor.shutdown();
	}

}
