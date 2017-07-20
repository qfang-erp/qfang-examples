package com.qfang.examples.redis.jedis.spring.serializer;

import org.junit.Test;

import com.qfang.examples.redis.jedis.spring.JedisSpringBaseTests;
import com.qfang.examples.redis.performance.Job;
import com.qfang.examples.redis.performance.PerformanceTestBase;

/**
 * 测试 jedis 使用不同序列化方式时的性能差异
 * 使用普通的 string 类型的 key - value 验证多线程插入时的性能
 * 实际测试时发现相同的环境，多次试验时，数据波动较大，目前还不清楚是什么原因导致...
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public class DefaultSerializerTests extends JedisSpringBaseTests {
	
	@Test
	public void defaultTest() throws InterruptedException {
		PerformanceTestBase.executeTest(new Job() {
			@Override
			public void execute(long index, String threadName) {
				redisTemplate.opsForValue().set(threadName + "-Key-" + index, "threadNameValue-" + index);
			}
		}, 20, 2000000);
	}
	
}
