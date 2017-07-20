package com.qfang.examples.java8.lambda;

import static java.util.Comparator.comparing;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

/**
 * 1、lambda 表达式的格式
 * 2、使用 lambda 表达式改写函数式接口
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
public class LambdaTests1 {
	
	/**
	 * 在 java 中向其他代码传递一段代码并不容易，你不可能将一个代码块到处传递。
	 * 由于 java 是一个面向对象的语言，因此你不得不构建一个属于某一个类的对象，由它的某个方法来包含所需的代码
	 * 就像 java 中很多这种函数式接口（只包含一个抽象方法的接口），就是用来封装代码的传递
	 * {@link Runnable#run()}
	 * {@link Comparator#compare(Object, Object)}
	 */
	@Test
	public void doWork() throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 1000; i++) {
					System.out.println(i);
				}
			}
		});
		t1.start();
		t1.join();
	}
	
	/**
	 * 使用 lambda 表达式实现 {@link #doWork()} 方法的功能
	 * 从该示例中可以看出 lambda 表达式的格式如下：
	 * 参数、箭头 ->，以及一个表达式
	 * ()   ->   { // do something ... } 
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void doWorkWithLambda() throws InterruptedException {
		Thread t1 = new Thread(() -> {
			for(int i = 0; i < 1000; i++) {
				System.out.println(i);
			}
		});
		t1.start();
		t1.join();
	}
	
	@Test
	public void orderString() {
		List<String> strs = Lists.newArrayList("hello", "java", "and", "lambda");
		Collections.sort(strs, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
		});
		strs.forEach(System.out::println);
	}
	
	/**
	 * lambda 表达式示例
	 * 
	 * (String o1, String o2) -> {
	 *		return Integer.compare(o1.length(), o2.length());
	 * }
	 * 
	 * (o1, o2) -> {
	 *		return Integer.compare(o1.length(), o2.length());
	 * }
	 * 
	 * 因为代码块里面的代码只有简单的一句，因此代码块的 {} 也可以省略，而且连 return 语句也可以省略
	 * (o1, o2) -> Integer.compare(o1.length(), o2.length())
	 * 
	 * 你可以像对待方法参数一样向 lambda 表达式的参数添加注解或者 final 修饰符
	 * (final String o1, @NotNull String o2) 
	 * 
	 */
	@Test
	public void orderStringWithLambda() {
		List<String> strs = Lists.newArrayList("hello", "java", "and", "lambda");
		
		Collections.sort(strs, (String o1, String o2) -> {
			return Integer.compare(o1.length(), o2.length());
		});
		
		// 因为这里两个参数的类型可以自动推导所以这里参数的类型也可以省略
		Collections.sort(strs, (o1, o2) -> {
			return Integer.compare(o1.length(), o2.length());
		});
		
		// return 语句也不是必须的，不需要 return 是 {} 也可以省略
		Collections.sort(strs, (o1, o2) -> Integer.compare(o1.length(), o2.length()));
		
		// 使用 Comparator#comparing 来生成 Comparator
		// 这个时候只有一个参数，参数的括号也可以省略
		Collections.sort(strs, comparing(str -> str.length()));
		
		// 使用方法引用时，参数也不需要了
		Collections.sort(strs, comparing(String::length));
		
		strs.forEach(System.out::println);
	}
	
}
