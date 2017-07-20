package com.qfang.examples.redis.jedis.pool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年3月9日
 * @since 1.0
 */
public class HashTypeTests extends JedisTestBase {

	@Test
	public void testSetGet() {
		// 测试增加、获取和删除
		Jedis jedis = getResources();
		jedis.hset("hset", "field1", "value1");
		jedis.hset("hset", "field2", "value2");
		
		assertThat(jedis.hget("hset", "field2")).isEqualTo("value2");
		jedis.hdel("hset", "field1");
		assertThat(jedis.hget("hset", "field1")).isNull();
	}
	
	@Test
	public void testHMSet() {
		// 测试批量插入和批量获取 mset,mget
		Jedis jedis = getResources();
		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", "value1");
		map.put("field2", "value2");
		map.put("field3", "value3");
		jedis.hmset("testHMSet", map);
		
		assertThat(jedis.hmget("testHMSet", "field1", "field2")).isEqualTo(Arrays.asList("value1", "value2"));
	}
	
	@Test
	public void testHGetAll() {
		// 测试获取hash所有值和所有的key
		Jedis jedis = getResources();
		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", "value1");
		map.put("field2", "value2");
		map.put("field3", "value3");
		jedis.hmset("testHGetAll", map);
		
		assertThat(jedis.hgetAll("testHGetAll")).isEqualTo(map);
		assertThat(jedis.hkeys("testHGetAll")).isEqualTo(map.keySet());
	}

}
