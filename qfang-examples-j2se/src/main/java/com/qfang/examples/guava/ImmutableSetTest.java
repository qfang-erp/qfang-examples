package com.qfang.examples.guava;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Collections.unmodifiableXXX 与 Guava 中 ImmutableXXX 特性的比较
 * 
 * Collections.unmodifiableXXX 中的 collection 直接指向源 collection，所以源 collection 的任何改变都会被反映到 unmodifiableXXX 中
 * ImmutableXXX 通过 copy 的方式将源 collection 中的元素 copy 到 ImmutableXXX 中，之后源 collection 的变化不会影响到 ImmutableXXX 的变化，除了引用对象内部属性改变
 * 
 * @author liaozhicheng
 * @date 2016年9月6日
 * @since 1.0
 */
public class ImmutableSetTest {

	public static void main(String[] args) {
		// Collections.unmodifiableSet 当源集合对象发送变化时，增加或删除元素，unmodifiableSet 中的元素也跟着发生了变化
		// 原因是因为 UnmodifiableCollection 构造方法中直接使用 this.c = c; 
		// 即  unmodifiableSet 中的 collection 直接指向源 collection 对象的引用
		
		// Guava 中的 ImmutableSet 是通过 copy 的方式，将源 collection 中的每个元素 copy 到 ImmutableSet 中
		// 所以 copy 之后源 collection 中的元素的增加或删除不会影响 ImmutableSet 中元素的变化
		Set<String> set0 = Sets.newHashSet("a", "b");
		
		Set<String> unmodifiableList0 = Collections.unmodifiableSet(set0);
		Set<String> immutableSet0 = ImmutableSet.copyOf(set0);
		set0.add("c");
		System.out.println(unmodifiableList0.stream().collect(joining(",")));  // a,b,c
		System.out.println(immutableSet0.stream().collect(joining(",")));  // a,b
		
		
		// 如果集合中保存的是对象的引用，如果直接通过对象的引用修改对象的属性，那么两种方式里面对象的属性对会改变
		// 所以 Guava 中的 ImmutableSet 对于对象的 copy 只是引用的 copy，并不是新建一个对象的实例
		Person p = new Person("b", 2);
		Set<Person> set = Sets.newHashSet(p);
		
		Set<Person> unmodifiableList = Collections.unmodifiableSet(set);
		Set<Person> immutableSet = ImmutableSet.copyOf(set);
		p.name = "p2";  // 直接通过对象的引用修改属性值
		unmodifiableList.stream().forEach(System.out::println);  // Person{name=p2, age=2}
		immutableSet.stream().forEach(System.out::println);  // Person{name=p2, age=2}
	}
	
	
	static class Person {
		
		String name;
		int age;
		
		Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("name", name)
					.add("age", age)
					.toString();
		}
		
	}
	
	
}
