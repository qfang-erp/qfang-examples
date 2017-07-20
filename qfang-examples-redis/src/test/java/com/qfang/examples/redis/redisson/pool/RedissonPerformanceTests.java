package com.qfang.examples.redis.redisson.pool;

import com.qfang.examples.redis.performance.PerformanceTestBase;
import org.junit.Test;
import org.redisson.core.RBucket;

import com.qfang.examples.redis.performance.Job;

/**
 * redisson 客户端性能测试
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public class RedissonPerformanceTests extends RedissonTestBase {
	
	@Test
	public void defaultTest() throws InterruptedException {
		// 测试使用 redisson 进行普通的 key/value 插入操作
		PerformanceTestBase.executeTest(new Job() {
			@Override
			public void execute(long index, String threadName) {
				String key = threadName + index;
				RBucket<String> bucket = redisson.getBucket(key);
				bucket.set(threadName);
			}
		}, 20, 1000000);
	}
	
}
