package com.qfang.examples.java8.stream;

import java.util.stream.Stream;

import org.junit.Test;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月28日
 * @since 1.0
 */
public class StreamAdvancedTests {
	
	@Test
	public void streamWorkTest() {
		// 看这个示例先对流进行 filter 操作，再进行了一次 forEach
		// 从代码上来看 filter 方法是需要对集合的整个元素进行一次迭代，forEach 方法又需要对 filter 之后的流进行一次全迭代
		// 理论上不是要比自己写 for 循环，然后在循环里面处理所有逻辑要效率更低吗？（上面需要循环多次，这里只需要循环一次）
		
		// 该语句到底是下面哪种输出呢？
		// d2, a2, b1, b3, c, d2, a2, b1, b3, c  循环多次，每次进行一个操作，效率更低
		// d2, d2, a2, a2, b1, b1, b3, b3, c, c  一次循环，处理所有操作，效率更高
		Stream.of("d2", "a2", "b1", "b3", "c")
			.filter(s -> {
				System.out.println("filter: " + s);
				return true;
			})
			.forEach(s -> System.out.println("forEach: " + s));
	}
	
	@Test
	public void mapFilterTest() {
		// filter 操作应该尽量出现在前面
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .filter(s -> {
		        System.out.println("filter: " + s);
		        return s.startsWith("A");
		    })
		    .forEach(s -> System.out.println("forEach: " + s));
		
		// map:     d2
		// filter:  D2
		// map:     a2
		// filter:  A2
		// forEach: A2
		// map:     b1
		// filter:  B1
		// map:     b3
		// filter:  B3
		// map:     c
		// filter:  C
	}
	
	@Test
	public void filterMapTest() {
		// 结合着两个示例来看，第二个操作需要迭代处理元素的个数明显比第一个要少
		// 所以当有多个中间操作时，应该将 filter 这类操作放在前面，先把流的元素过滤出来
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .filter(s -> {
		        System.out.println("filter: " + s);
		        return s.startsWith("a");
		    })
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .forEach(s -> System.out.println("forEach: " + s));

		// filter:  d2
		// filter:  a2
		// map:     a2
		// forEach: A2
		// filter:  b1
		// filter:  b3
		// filter:  c
	}
	
	@Test
	public void sortFilterMapTest() {
		// 先排序，后过滤（不建议这么做）
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .sorted((s1, s2) -> {
		        System.out.printf("sort: %s; %s\n", s1, s2);
		        return s1.compareTo(s2);
		    })
		    .filter(s -> {
		        System.out.println("filter: " + s);
		        return s.startsWith("a");
		    })
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .forEach(s -> System.out.println("forEach: " + s));
	}
	
	@Test
	public void filterSortMapTest() {
		// 变换写法之后，排序一次都没有了，map 也只有一次
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .filter(s -> {
		        System.out.println("filter: " + s);
		        return s.startsWith("a");
		    })
		    .sorted((s1, s2) -> {
		        System.out.printf("sort: %s; %s\n", s1, s2);
		        return s1.compareTo(s2);
		    })
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .forEach(s -> System.out.println("forEach: " + s));

		// filter:  d2
		// filter:  a2
		// filter:  b1
		// filter:  b3
		// filter:  c
		// map:     a2
		// forEach: A2
	}
	
}
