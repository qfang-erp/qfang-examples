package com.qfang.examples.redis.jedis.pool;

import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年3月10日
 * @since 1.0
 */
public class SetTypeTests extends JedisTestBase {

	@Test
	public void testSetGet() {
		Jedis jedis = getResources();
		jedis.sadd("testSet", "value1", "value2", "value3");
		
		MatcherAssert.assertThat(jedis.smembers("testSet"), Matchers.containsInAnyOrder("value1", "value2", "value3"));
	}
	
	@Test
	public void testIsMember() {
		Jedis jedis = getResources();
		jedis.sadd("testIsMember", "value1", "value2", "value3", "value1");
		
		assertThat(jedis.sismember("testIsMember", "value2")).isTrue();
	}
	
	@Test
	public void testUnion() {
		Jedis jedis = getResources();
		jedis.sadd("testSet1", "value1", "value2");
		jedis.sadd("testSet2", "value2", "value3");
		jedis.sunionstore("testSet3", "testSet1", "testSet2");
		
		MatcherAssert.assertThat(jedis.smembers("testSet3"), Matchers.containsInAnyOrder("value1", "value2", "value3"));
	}
	
}
