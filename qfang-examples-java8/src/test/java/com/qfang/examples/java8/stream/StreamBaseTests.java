package com.qfang.examples.java8.stream;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * 1、Stream 的基本概念介绍及一个简单的列表过滤和统计的示例
 * 
 * 一个 Stream 表面上看和集合很类似，允许你改变和获取数据，但实际上它与集合有很大的区别
 * a) Stream 自己不会存储元素，元素存储在底层的集合中
 * b) Stream 操作不会改变源对象
 * c) Stream 操作符可能是延迟执行的，这意味着它会等到需要结果的时候才执行
 * 
 * 当你使用一个 Stream 时，你会通过三个阶段来建立一个操作流水线
 * a) 创建一个 Stream <code> strs.stream(); </code>
 * b) 在一个或多个步骤中，将初始 Stream 转换为另一个 Stream 的中间操作  <code> stream.filter(s -> s.length() > 2); </code>
 * c) 使用一个终止操作来产生一个结果，该操作会强制它之前的延迟操作立即执行。并且在这之后，该 Stream 就不会被使用了 <code> stream.count(); </code>
 * 
 * Stream 不是集合元素，它不是数据结构并不保存数据，它是有关算法和计算的，它更像一个高级版本的 Iterator。
 * Stream 就如同一个迭代器（Iterator），单向，不可往复，数据只能遍历一次，遍历过一次后即用尽了，就好比流水从面前流过，一去不复返。
 * 而和迭代器又不同的是，Stream 可以并行化操作，迭代器只能命令式地、串行化操作。
 * Stream 的并行操作依赖于 Java7 中引入的 Fork/Join 框架来拆分任务和加速处理过程。
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月17日
 * @since 1.0
 */
public class StreamBaseTests {
	
	@Test
	public void countWordTest() {
		// 给定一个字符串列表，统计其中长度大于5的字符串个数
		List<String> words = TextWord.words();
		int count = 0;
		for(String str : words) {
			if(str.length() > 5)
				count++;
		}
		System.out.println(count);
	}
	
	@Test
	public void countWordWithStream() {
		// 使用 java 8 的 stream 流的方式改写上面的示例，只用一行代码就搞定
		long count = TextWord.words().stream().filter(s -> s.length() > 5).count();
		System.out.println(count);
	}
	
	@Test
	public void parallelCountWordTest() {
		// 分分钟将上面的操作变成并行处理，充分利用多核cpu服务器的性能，而且完全不需要自己手写并发处理的代码
		// 只需要将 stream 改成 parallelStream 方法，就可以让 Stream API 并行执行过滤和统计操作
		long count = TextWord.words().parallelStream().filter(s -> s.length() > 5).count();
		System.out.println(count);
	}
	
	@Test(expected = IllegalStateException.class)
	public void streamTest() {
		Stream<String> stream = TextWord.words().stream();
		long count = stream.filter(s -> s.startsWith("a")).count();
		System.out.println(count);
		
		// 当一个流对象碰到结束操作（如上面的 count 操作）后，这个流就不能再被使用了
		// 这里将抛出 IllegalStateException
		stream.forEach(System.out::println);
		
		// 如果想要实现上面的效果，既要打印出来每个元素（方便调试），又要进行统计
		// 这里 foEach 和 count 两个操作都是终止操作，如何实现在一个流上面进行两个终止操作
		// 可以使用 peek 方法，复制一个与原始流一样的流，见下面的 peekTest
	}
	
	@Test
	public void peekTest() {
		Stream<String> stream = TextWord.words().stream();
		stream.filter(s -> s.startsWith("a")).peek(System.out::println).count();
		
		// 注意这两个的差别，上面的是对过滤后的流进行复制，而下面的是对过滤前的流进行复制
		// stream.peek(System.out::println).filter(s -> s.startsWith("a")).count(); 
	}
	
	@Test
	public void streamSupplierTest() {
		// 通过 lambda 表达式返回一个函数接口
		Supplier<Stream<String>> streamSupplier = () -> TextWord.words().stream().filter(s -> s.startsWith("a"));
		
		// 在这个函数接口上每次调用 get 方法，都将获得新的流
		System.out.println(streamSupplier.get().count());
		System.out.println(streamSupplier.get().anyMatch(x -> x.equals("an")));
	}
	
}
