package com.qfang.examples.redis.jedis.spring.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * Kryo 对象是非线程安全的
 * 因为 Kryo 对象的创建比较重量级，所以这里使用对象池来管理 Kryo 对象
 * 详见官方说明：https://github.com/EsotericSoftware/kryo
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public final class KryoHolder {
	
	private static final KryoFactory kryoFacoty = new SimpleKryoFactory();
	
	private static final KryoPool pool = new KryoPool.Builder(kryoFacoty).softReferences().build();
	
	public static Kryo borrow() {
		return pool.borrow();
	}
	
	public static void release(Kryo kryo) {
		pool.release(kryo);
	}
	
	private static class SimpleKryoFactory implements KryoFactory {
		
		@Override
		public Kryo create() {
			Kryo kryo = new Kryo();
			// configure kryo instance, customize settings
			return kryo;
		}
	};
	
}
