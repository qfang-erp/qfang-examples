package com.qfang.examples.redis.redisson.pool;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;

import com.qfang.examples.redis.TestConfig;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年3月8日
 * @since 1.0
 */
public class RedissonTestBase {

	protected static RedissonClient redisson;

	@BeforeClass
	public static void initRedisson() {
		Config config = new Config();
		config.useSingleServer()
				.setAddress(TestConfig.HOST_NAME + ":" + TestConfig.PORT)
				.setConnectionPoolSize(100);
		redisson = Redisson.create(config);
	}

	@AfterClass
	public static void shutdown() {
		if (redisson != null) {
			redisson.getKeys().flushall();
			redisson.shutdown();
		}
	}

}
