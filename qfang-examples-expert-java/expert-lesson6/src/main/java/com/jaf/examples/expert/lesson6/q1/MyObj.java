package com.jaf.examples.expert.lesson6.q1;


import static java.util.stream.Collectors.toMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import com.jaf.examples.expert.common.utils.ReflectUtils;
import com.jaf.examples.expert.common.utils.UnsafeUtils;

import sun.misc.Unsafe;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年11月17日
 * @since 1.0
 */
public class MyObj extends MyParentObj {
	
	int mint1;
	byte mbyte;
	int mint2;
	boolean mboolean1;
	float mfloat;
	short mshort;
	long mlong;
	Object mobj;
	boolean mboolean2;
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		printAllFieldOffset(MyObj.class);
	}
	
	private static void printAllFieldOffset(Class<?> clazz) {
		Unsafe unsafe = UnsafeUtils.getUnsafe();
		Map<Long, Field> map = Arrays.stream(ReflectUtils.getAllDeclaredFields(clazz))
				.collect(toMap(
						unsafe::objectFieldOffset,
						Function.identity(), 
						(a, b) -> a,
						TreeMap::new
				));
		map.entrySet().forEach(e -> {
			Field field = e.getValue();
			String className = field.getDeclaringClass().getSimpleName();
			System.out.println(className + "." +  field.getName() + " : " + e.getKey());
		});
	}

}


class Test {
	
	int a;
	byte b;
	int c;

	
}

class MyParentObj extends MySuperObj {
	
	long mslong;
	byte msbyte;
	
}

class MySuperObj {
	
	long slong;
	
}
