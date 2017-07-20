package com.qfang.examples.redis.jedis.pool;

import com.qfang.examples.redis.TestConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年3月9日
 * @since 1.0
 */
public class JedisTestBase {
	
	private static JedisPool pool;
	
	@BeforeClass
	public static void initPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(50);
		config.setMaxIdle(10);
		config.setMaxWaitMillis(600);
		config.setTestOnBorrow(true);
		
		pool = new JedisPool(config, TestConfig.HOST_NAME, TestConfig.PORT);
	}
	
	
	@After
	public void flushAll() {
		//pool.getResource().flushAll();
	}
	
	protected Jedis getResources() {
		return pool.getResource();
	}
	
	@AfterClass
	public static void shutdown() {
		pool.close();
	}
	
	@Test
	public void testSave() {
		Jedis jedis = getResources();
		for(int i = 0; i < 1000; i++) {
			String key = "testa" + i;
			byte[] value = new byte[1024];
			jedis.set(key.getBytes(), value);
		}
	}

	
//	static abstract class RedisCallback {
//		
//		public void exec() {
//			Jedis connection = pool.getResource();
//			doInRedis(connection);
//			pool.returnResource(connection);
//		}
//		
//		abstract void doInRedis(Jedis connection);
//		
//	}
	
}
