package com.qfang.examples.java8.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

/**
 * 1、Stream 的创建方式
 * 	 通过数组，集合的方式创建 Stream 示例
 * 2、Stream 静态方法 generate & iterate 创建无限流示例
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月17日
 * @since 1.0
 */
public class StreamCreateTests {
	
	@Test
	public void createStreamTest() {
		// 数组通过 Stream.of 静态方法或者 Arrays.stream 方法创建 Stream 对象
		String[] strArr = new String[] {"a", "b", "c"};
		Stream<String> s1 = Stream.of(strArr);
		assertThat(s1.count()).isEqualTo(3);
		
		Stream<String> s11 = Stream.of("aa", "bb");
		assertThat(s11.count()).isEqualTo(2);
		
		Stream<String> s2 =  Arrays.stream(strArr);
		assertThat(s2.count()).isEqualTo(3);
		
		// 集合可以直接通过集合对象上的 stream 方法创建（Collection 接口中声明）
		List<String> strs = Arrays.asList(strArr);
		Stream<String> s3 = strs.stream();
		assertThat(s3.count()).isEqualTo(3);
		
		// 创建一个空的 Stream
		Stream<String> emptyStrem = Stream.empty();
		assertThat(emptyStrem.count()).isZero();
		
		// 创建含有一个常量的 Stream
		Stream<String> echos = Stream.generate(() -> "Echo");
		assertThat(echos.findFirst().get()).isEqualTo("Echo");
	}
	
	@Test
	public void streamGenerateTest() {
		// 创建无限流的两种方式 Stream.generate & Stream.iterate
		
		// 注： Stream.generate(Math::random) 并不会立即无限创建随机数，因为 Stream 操作是延迟执行，只有碰到终止操作才会执行
		// Stream.generate(Math::random).limit(100) 创建100个随机数，如果后面调用的不是 limit 方法，而是 count 方法，则会无限制创建下去
		Stream<Double> randoms = Stream.generate(Math::random).limit(100);
		assertThat(randoms.count()).isEqualTo(100);
	}
	
	
	@Test
	public void streamIterateTest() {
		// iterate 操作，第一个参数为种子值，第二个参数为一个操作代码/函数，表示将种子值进行该操作后得到第二个值，并且依次循环得到后面的值
		// 1,2,3,4,.. 100
		Stream<Integer> integers = Stream.iterate(1, x -> x + 1).peek(System.out::println).limit(10);
		Optional<Integer> max = integers.max(Integer::compareTo);
		assertThat(max.isPresent()).isTrue();
		assertThat(max.get()).isEqualTo(10);

		Cache<String, Object> cache = CacheBuilder.newBuilder()
				.initialCapacity(20)
				.maximumSize(50)
				.recordStats()
				.expireAfterWrite(1, TimeUnit.HOURS)
				.removalListener(notification -> System.out.println(notification.getValue()))
				.build();
	}
	
}
