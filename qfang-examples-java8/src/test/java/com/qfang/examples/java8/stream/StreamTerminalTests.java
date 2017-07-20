package com.qfang.examples.java8.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.assertj.core.util.Lists;
import org.junit.Test;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月29日
 * @since 1.0
 */
public class StreamTerminalTests {
	
	private static final List<Integer> nums = Arrays.asList(1, 5, null, 3, 5, 10, 7, 11);
	
	@Test
	public void minTest() {
		assertThat(
			nums.stream()
					.filter(n -> n != null)
					.min(Integer::compareTo)
					.get()).isEqualTo(1);
	}
	
	@Test
	public void maxTest() {
		assertThat(
			nums.stream()
				.filter(n -> n != null)
				.max(Integer::compareTo)
				.get()).isEqualTo(11);
	}
	
	@Test
	public void countTest() {
		assertThat(nums.stream().count()).isEqualTo(8);
	}
	
	@Test
	public void findFirstTest() {
		// findFirst: 它总是返回 Stream 的第一个元素，或者空。
		IntConsumer consumer = (int i) -> {
			Person.persons()
				.stream()
				.parallel()
				.findFirst()
				.ifPresent(System.out::println);
		};
		
		IntStream.range(0, 10).forEach(consumer);
	}
	
	@Test
	public void findAnyTest() {
		IntConsumer consumer = (int i) -> {
			Person.persons()
				.stream()
				.parallel()
				.findAny()
				.ifPresent(System.out::println);
		};
		
		IntStream.range(0, 100).forEach(consumer);
	}
	
	@Test
	public void anyMatchTest() {
		// anyMatch 只要找到一个匹配的元素循环就好立即返回
		Person.persons()
			.stream()
			.anyMatch(p -> {
				System.out.println(p);
				return p.age > 18;
			});
	}

	@Test
	public void reduceSimpleTest() {
		int sums = nums.stream()
				.filter(n -> n != null)
				.reduce(0, (sum, item) -> sum + item);
		System.out.println(sums);
	}
	
	@Test
	public void reduceObjectTest() {
		// 对象的列表，使用 reduce
		Person result = Person.persons()
				.stream()
				.reduce(new Person("", 0), (p1, p2) -> {
					p1.name += (p2.name + ",");
					p1.age += p2.age;
					return p1;
				});
		System.out.format("name=%s; age=%s \n", result.name, result.age);
	}
	
	@Test
	public void reduceMaxTest() {
		// 使用 reduce 查找 age 最大的用户
		Person.persons()
		    .stream()
		    .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
		    .ifPresent(System.out::println);    // Pamela
	}
	
	@Test
	public void collectToListTest() {
		// 收集 stream 处理后的结果到新的 List/Set
		List<Person> numList = Person.persons()
				.stream()
				.filter(p -> p.age > 18)
				.collect(Collectors.toList()); // Collectors.toSet()
		
		numList.forEach(System.out::println);
	}

	@Test
	public void collectJoinTest() {
		// joining, 将 List 连接成字符串
		String names = Person.persons()
				.stream()
				.map(p -> p.name)
				.collect(Collectors.joining(", "));
		
		System.out.println(names);
	}
	
	@Test
	public void collectToMapTest() {
		// 将一个 list 映射成 map，Collectors.toMap 第一个参数为 map 的 key　的值，第二个为 map 对应的 value 的值
		Map<String, Person> personNameMap = Person.persons()
				.stream()
				.collect(Collectors.toMap(Person::getName, Function.identity()));  // Function.identity() 表示元素本身，这里为 person 对象
		personNameMap.forEach((key, value) -> {
			System.out.format("personNameMap item : { key : %s, value : %s }\n", key, value);
		});
	}
	
	@Test
	public void collectAverageTest() {
		// 计算人员的平均值
		Double averageAge = Person.persons()
			    .stream()
			    .collect(Collectors.averagingInt(p -> p.age));
		System.out.println(averageAge);     // 19.0
	}
	
	@Test
	public void collectSummarizingTest() {
		// summarizingInt 对 List Integer 集合取最大值，最小值，平均值，总和，总数一次性处理
		// 同样的处理 Collectors.summarizingDouble 数组，summarizingLong 数组
		IntSummaryStatistics summary = Person.persons()
				.stream()
				.filter(n -> n != null)
				.collect(Collectors.summarizingInt(Person::getAge));
		System.out.format("age average: %s, max age: %s, min age: %s, sum age: %s \n", 
				summary.getAverage(), summary.getMax(), summary.getMin(), summary.getSum());
	}
	
	@Test
	public void collectGroupByTest() {
		// 对 List 进行分组
		Map<Integer, List<Person>> ageGroupMap = Person.persons()
				.stream()
				.collect(Collectors.groupingBy(Person::getAge));  // 根据人员的年龄进行分组
		
		ageGroupMap.forEach((key, valList) -> {
			String valListToString = valList.stream()
					.map(Person::toString)
					.collect(Collectors.joining(" , ", "[ ", " ]"));
			System.out.format("ageGroupMap item : { key : %s, value: %s }\n", key, valListToString);
		});
	}
	
	@Test
	public void collectGroupByThenCountTest() {
		// 分组后统计
		// Collectors.groupingBy 方法第二个参数接收一个函数，用来对分组后的结果进行进一步处理
		Map<Integer, Long> ageGroupSumMap = Person.persons()
				.stream()
				.collect(Collectors.groupingBy(Person::getAge, Collectors.counting()));
		ageGroupSumMap.forEach((key, value) -> {
			System.out.format("ageGroupSumMap item: { key: %s, value: %s }\n", key, value);
		});
	}
	
	
	static class Person {

		String name;
		int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}
		
		public String getName() {
			return name;
		}
		
		public int getAge() {
			return age;
		}

		@Override
		public String toString() {
			return name + " | " + age;
		}

		static List<Person> persons() {
			return Lists.newArrayList(
				new Person("Max", 18),
		        new Person("Peter", 23),
		        new Person("Pamela", 23),
		        new Person("David", 12)
			);
		}
		
	}
	
}
