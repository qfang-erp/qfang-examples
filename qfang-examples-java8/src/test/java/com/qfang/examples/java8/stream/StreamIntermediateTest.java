package com.qfang.examples.java8.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Intermediate
 * 
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月29日
 * @since 1.0
 */
public class StreamIntermediateTest {
	
	static final List<String> strList = Arrays.asList("aB", "ac", "Bc");
	
	@Test
	public void filterTest() {
		assertThat(strList.stream().filter(s -> s.startsWith("a")).count()).isEqualTo(2);
	}
	
	@Test
	public void mapTest() {
		// 给所有元素添加一个后缀
		Stream<String> streamSuffix = strList.stream().map(s -> s + "_d");
		assertThat(streamSuffix.findFirst().get()).isEqualTo("aB_d");
		
		// 将所有元素转换成小写
		Stream<String> lowerCaseWords = strList.stream().map(String::toLowerCase);
		assertThat(lowerCaseWords.findFirst().get()).isEqualTo("ab");
		
		Stream<Character> firstChars = strList.stream().map(s -> s.charAt(0));
		assertThat(firstChars.peek(System.out::println).findFirst().get()).isEqualTo('a');
	}
	
	@Test
	public void flatMapTest() {
		// [['a', 'B'], ['a', 'c'], ['B', 'c']]
		Stream<Stream<Character>> stream = strList.stream().map(s -> characterStream(s));
		stream.forEach(System.out::println);
		
		// ['a', 'B', 'a', 'c', 'B', 'c'] 将后面的数组展开并合并到第一个数组中
		Stream<Character> stream2 = strList.stream().flatMap(s -> characterStream(s));
		stream2.forEach(System.out::println);
	}
	
	private static Stream<Character> characterStream(String s) {
		List<Character> result = new ArrayList<>();
		for(char c : s.toCharArray()) {
			result.add(c);
		}
		return result.stream();
	}
	
	@Test
	public void distinctTest() {
		// 去重逻辑依赖元素的equals方法
		TextWord.words()
			.stream()
			.filter(s -> s.startsWith("ab"))
			.distinct()
			.forEach(System.out::println);
	}
	
	@Test
	public void limitTest() {
		TextWord.words()
			.stream()
			.limit(20)
			.forEach(System.out::println);
	}
	
	@Test
	public void skipTest() {
		TextWord.words()
			.stream()
			.filter(s -> s.startsWith("ab"))
			.distinct()
			.skip(2)
			.forEach(System.out::println);
	}
	
	@Test
	public void sortTest() {
		TextWord.words()
			.stream()
			.filter(s -> s.length() > 12)
			.sorted(Comparator.comparing(String::length))
			.forEach(System.out::println);
	}
	
	@Test
	public void intermediateTest() {
		Arrays.asList(0, 1, 5, null, 2, 7, 9, 5, 3, null, 5, 7, 4, 8, 6)
				.stream()
				.filter(i -> i != null)
				.map(i -> "int: " + i)
				.distinct()
				.sorted()
				.limit(10)
				.skip(2)
				.peek(s -> s.toUpperCase())
				.parallel()
				.collect(Collectors.toList())
				.forEach(System.out::println);
	}
	
}
