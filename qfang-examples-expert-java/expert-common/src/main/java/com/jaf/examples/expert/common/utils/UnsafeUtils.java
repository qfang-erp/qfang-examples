package com.jaf.examples.expert.common.utils;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月10日
 * @since 1.0
 */
public final class UnsafeUtils {
	
	private static final Unsafe unsafe;
	
	static {
		try {
			Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
			singleoneInstanceField.setAccessible(true);
			unsafe = (Unsafe) singleoneInstanceField.get(null);
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	public static Unsafe getUnsafe() {
		return unsafe;
	}
	
}
