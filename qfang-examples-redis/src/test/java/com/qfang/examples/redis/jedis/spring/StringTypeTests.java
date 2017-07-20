package com.qfang.examples.redis.jedis.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年3月11日
 * @since 1.0
 */
public class StringTypeTests extends JedisSpringBaseTests {

	@Test
	public void testSetAndGet() {
		String value = "testValue";
		redisTemplate.opsForValue().set("testSetAndGet", value);
		assertThat(redisTemplate.opsForValue().get("testSetAndGet")).isEqualTo(value);
	}
	
	@Test
	public void testSetNx() {
		assertThat(redisTemplate.opsForValue().setIfAbsent("testSetNx", "testValue")).isTrue();
		assertThat(redisTemplate.opsForValue().setIfAbsent("testSetNx", "testValueNew")).isFalse();
		
		assertThat(redisTemplate.opsForValue().get("testSetNx")).isEqualTo("testValue");
	}
	
	@Test
	public void testTimeOut() throws InterruptedException {
		redisTemplate.opsForValue().set("testTimeOut", "testTimeOutValue", 500, TimeUnit.MILLISECONDS);
		assertThat(redisTemplate.opsForValue().get("testTimeOut")).isEqualTo("testTimeOutValue");
		
		Thread.sleep(510);
		
		assertThat(redisTemplate.opsForValue().get("testTimeOut")).isNull();
	}
	
}
