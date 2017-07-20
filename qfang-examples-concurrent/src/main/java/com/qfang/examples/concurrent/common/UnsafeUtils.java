package com.qfang.examples.concurrent.common;

import sun.misc.Unsafe;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月10日
 * @since 1.0
 */
public final class UnsafeUtils {
	
	private static final Unsafe U;
	
	static {
		U = getU();
	}
	

	public static Unsafe getUnsafe() {
	    return U;
    }

	/**
	 * netty 里面的做法
	 * 
	 * @see io.netty.util.internal.chmv8.ForkJoinPool
	 * @return
	 */
	private static sun.misc.Unsafe getU() {
        try {
            return sun.misc.Unsafe.getUnsafe();
        } catch (SecurityException tryReflectionInstead) {}
        try {
            return java.security.AccessController.doPrivileged
                    (new java.security.PrivilegedExceptionAction<sun.misc.Unsafe>() {
                        public sun.misc.Unsafe run() throws Exception {
                            Class<sun.misc.Unsafe> k = sun.misc.Unsafe.class;
                            for (java.lang.reflect.Field f : k.getDeclaredFields()) {
                                f.setAccessible(true);
                                Object x = f.get(null);
                                if (k.isInstance(x))
                                    return k.cast(x);
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }});
        } catch (java.security.PrivilegedActionException e) {
            throw new RuntimeException("Could not initialize intrinsics",
                    e.getCause());
        }
    }
	
}
