package com.qfang.examples.java8.lambda;

import org.junit.Test;

/**
 * 1、lambda 表达式中变量的作用域
 * 2、Java 中的闭包
 * 3、lambda this 的指向（lambda 表单式的方法体与嵌套代码块有着相同的作用域）
 * 相对于内部类，lambda表达式的语义就十分简单：它不会从超类（supertype）中继承任何变量名，也不会引入一个新的作用域。
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
public class LambdaTests3 {
	
	@Test
	public void thisReferenceTest() throws InterruptedException {
		Application application = new Application();
		application.doWork();
		Thread.sleep(50);
	}
	
	
	class Application {
		
		public void doWork() {
			int num = 5;
			
			/**
			 * lambda 表单式的方法体与嵌套代码块有着相同的作用域
			 * 因此此处 lambda 表达式中的 this 指向的是 doWork 方法中的 this，即 Application 对象
			 */
			Runnable r = () -> {
//				 int num = 5;  // 错误，因为 lambda 表达式和嵌套的代码块有着相同的作用域，在同一个作用域下面不能声明两个同名的变量
//				 num = 6;   // 错误，被 lambda 表达式捕获的变量值不可以更改
				System.out.println(Thread.currentThread().getName() + "," + this.toString() + "," + num);  // this is application
			};
			new Thread(r, "thread-name: lambda").start();
			
			/**
			 * 正常的匿名内部类的写法，此时的 this 很显然是指向 Runnable 对象
			 */
			new Thread(new Runnable() {
				@Override
				public void run() {
//					int num = 6;  // 内部类其实也会捕获闭合作用域中的值，例如将这段注释掉，这里的 num=5
					System.out.println(Thread.currentThread().getName() + "," + this.toString() + "," + num);  // this is runnable
				}
				
				@Override
				public String toString() {
					return "this is runnable";
				}
			}, "thread-name: inner class").start();
		}

		@Override
		public String toString() {
			return "this is application";
		}
		
	}
	
}
