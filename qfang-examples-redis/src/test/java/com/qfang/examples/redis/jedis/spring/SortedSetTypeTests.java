package com.qfang.examples.redis.jedis.spring;

import static org.assertj.core.api.Assertions.assertThat;
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
public class SortedSetTypeTests extends JedisSpringBaseTests {

	@Test
	public void testSetGet() {
		redisTemplate.opsForZSet().add("testZset", "value1", 0.1);
		redisTemplate.opsForZSet().add("testZset", "value2", 0.2);
		redisTemplate.opsForZSet().add("testZset", "value3", 0.2);
		
		redisTemplate.opsForZSet().range("testZset", 0, -1);
		MatcherAssert.assertThat(redisTemplate.opsForZSet().range("testZset", 0, -1), 
				Matchers.containsInAnyOrder("value2", "value1", "value3"));
	
		MatcherAssert.assertThat(redisTemplate.opsForZSet().rangeByScore("testZset", 0.2, 0.2), 
				Matchers.containsInAnyOrder("value2", "value3"));
		
		assertThat(redisTemplate.opsForZSet().remove("testZset", "value2")).isEqualTo(1);
	}
	
	
}
