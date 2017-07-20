package com.qfang.examples.redis.jedis.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

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
public class HashTypeTests extends JedisSpringBaseTests {

	@Test
	public void testSetGet() {
		Map<String, String> user = new HashMap<String, String>();
		user.put("id", "1");
		user.put("name", "zhangsan");
		user.put("age", "20");
		user.put("address", "new address");
		redisTemplate.opsForHash().putAll("testHash", user);
		
		assertThat(redisTemplate.opsForHash().get("testHash", "name")).isEqualTo("zhangsan");
		assertThat(redisTemplate.opsForHash().size("testHash")).isEqualTo(4);
	}

	@Test
	public void testKeys() {
		Map<String, String> user = new HashMap<String, String>();
		user.put("id", "1");
		user.put("name", "zhangsan");
		user.put("age", "20");
		user.put("address", "new address");
		redisTemplate.opsForHash().putAll("testKeys", user);
		
		MatcherAssert.assertThat(redisTemplate.opsForHash().keys("testKeys"), 
				Matchers.containsInAnyOrder("id", "name", "age", "address"));
		MatcherAssert.assertThat(redisTemplate.opsForHash().values("testKeys"), 
				Matchers.containsInAnyOrder("1", "zhangsan", "20", "new address"));
	}
	
}
