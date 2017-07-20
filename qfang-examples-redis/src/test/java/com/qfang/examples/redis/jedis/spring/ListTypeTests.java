package com.qfang.examples.redis.jedis.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年3月12日
 * @since 1.0
 */
public class ListTypeTests extends JedisSpringBaseTests {

	@Test
	public void testSetGet() {
		redisTemplate.opsForList().leftPushAll("testList1", Arrays.asList("value1", "value2"));
		redisTemplate.opsForList().rightPush("testList1", "value3");
		
		MatcherAssert.assertThat(redisTemplate.opsForList().range("testList1", 0, -1), 
				Matchers.contains("value2", "value1", "value3"));
	}
	
	@Test
	public void testPop() {
		redisTemplate.opsForList().leftPushAll("testPop", Arrays.asList("value1", "value2", "value3"));
		
		assertThat(redisTemplate.opsForList().leftPop("testPop")).isEqualTo("value3");
		assertThat(redisTemplate.opsForList().size("testPop")).isEqualTo(2);
	}
	
	@Test
	public void testExpired() throws InterruptedException {
		redisTemplate.opsForList().rightPushAll("key1", Arrays.asList("v1", "v2"));
		redisTemplate.expire("key1", 500, TimeUnit.MILLISECONDS);
		
		MatcherAssert.assertThat(redisTemplate.opsForList().range("key1", 0, -1), 
				Matchers.contains("v1", "v2"));
		
		Thread.sleep(1000);
		
		assertThat(redisTemplate.hasKey("key1")).isFalse();
	}
	
}
