package com.qfang.examples.redis.cluster.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qfang.examples.redis.jedis.spring.serializer.KryoSerializer;
import com.qfang.examples.redis.performance.Job;
import com.qfang.examples.redis.performance.PerformanceTestBase;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月24日
 * @since 1.0
 */
public class ClusterPerformanceTests extends ClusterSpringTestBase {
	
	@Autowired
	private KryoSerializer kryoSerializer;
	
	@Test
	public void defaultTest() throws InterruptedException {
		// 测试使用 redis cluster 的性能
		PerformanceTestBase.executeTest(new Job() {
			@Override
			public void execute(long index, String threadName) {
				String key = new StringBuilder(threadName).append("-key-").append(index).toString();
				String value = threadName;
				getConnection().set(kryoSerializer.serialize(key), kryoSerializer.serialize(value));
			}
		}, 20, 20000);
	}
	
}
