package com.qfang.examples.java8.concurrent;

import org.junit.After;
import org.junit.Test;

import java.util.concurrent.*;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月30日
 * @since 1.0
 */
public class CompletableFutureTests {
	
	private ExecutorService executorService = Executors.newFixedThreadPool(2);
	
	@Test
	public void futureTest() throws InterruptedException, ExecutionException {
		// Future 可以用来编写一些异步的操作，比如这里的 getName 方法是一个比较耗时的操作，我们可以异步地放到另外一个线程中执行
		// 而让主线程可以继续处理其他逻辑，当需要用到结果时通过 future.get 来阻塞等待到结果变的可用
		Future<String> future = executorService.submit(() -> {
			return getName();
		});
		
		// do other thing ...
		
		// future.get 方法将阻塞等待一直到结果变的可用
		String name = future.get();
		
		// 对结果进行进一步的处理
		String upperCaseName = name.toUpperCase();
		System.out.println(upperCaseName);
	}
	
	@Test
	public void completableFutureTest() throws InterruptedException, ExecutionException {
		// Future 缺少很重要的一块，没有一种简单的方式可以做到“当结果变的可用时，请按照这种方式来处理它”
		// 这正是 CompletableFuture 类所提供的关键特性
		
		// 使用 CompletableFuture 改写上面的示例
		// 这里的 thenApply 方法不会被阻塞，当第一个 Future（getName）完成时，它会将 Future 传递给 thenApply
		// thenApply 会返回另外一个 Future，然后传递给 thenAccept，这种方式的代码可读性会比之前的更好
		// 最重要的是现在所有的处理代码都在一起了，而你只需要指定希望完成什么，以及按照什么顺序完成就可以了（Future 流水线）
		CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
			return getName();
		}, executorService).thenApply(String::toUpperCase).thenAccept(System.out::println);
		
		cf.get();
	}
	
	String getName() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hello";
	}
	
	@After
	public void shutdown() {
		executorService.shutdown();
	}
	
}
