package com.qfang.examples.concurrent.part4;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class UnsafeTest {

	
	private static final Unsafe unsafe;
	private static final long ageOffset;
	
	static {
		try {
			Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
			singleoneInstanceField.setAccessible(true);
			unsafe = (Unsafe) singleoneInstanceField.get(null);
			ageOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("age"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Person person = new Person(30);
		System.out.println(person.getAge());
		
		unsafe.compareAndSwapInt(person, ageOffset, 30, 50);
		System.out.println(person.getAge());
	}
	
	private static class Person {
		private final int age;
		
		Person(int age) {
			this.age = age;
		}
		
		public int getAge() {
			return this.age;
		}
	}
	
	
}
