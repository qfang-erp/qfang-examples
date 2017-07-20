package com.qfang.examples.redis.jedis.pool;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年3月9日
 * @since 1.0
 */
public class StringTypeTests extends JedisTestBase {

	@Test
	public void testSetGet() {
		// 测试新增和获取
		Jedis jedis = getResources();
		String value = "testValue";
		jedis.set("test", value);
		Assert.assertEquals(value, jedis.get("test"));
	}
	
	@Test
	public void testSetNx() {
		// 测试setnx，设置成功返回1，设置不成功返回0
		Jedis jedis = getResources();
		assertThat(jedis.setnx("testSetNx", "setNxValue")).isEqualTo(1);
		assertThat(jedis.setnx("testSetNx", "setNxValueNew")).isEqualTo(0);
	}
	
	@Test
	public void testTimeOut() throws InterruptedException {
		// 测试自动超时
		Jedis jedis = getResources();
		jedis.set("testTimeOut", "testTimeOutValue", "NX", "PX", TimeUnit.MILLISECONDS.toMillis(500));
		assertThat(jedis.get("testTimeOut")).isEqualTo("testTimeOutValue");
		
		Thread.sleep(500);
		
		assertThat(jedis.get("testTimeOut")).isNull();
	}
	
}
