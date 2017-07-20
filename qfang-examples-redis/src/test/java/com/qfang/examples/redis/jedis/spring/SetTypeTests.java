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
public class SetTypeTests extends JedisSpringBaseTests {

	@Test
	public void testSetGet() {
		redisTemplate.opsForSet().add("testSet", "value1", "value2", "value3");
		
		assertThat(redisTemplate.opsForSet().isMember("testSet", "value2")).isTrue();
		MatcherAssert.assertThat(redisTemplate.opsForSet().randomMember("testSet"), 
				Matchers.isOneOf("value1", "value2", "value3"));
	}
	
	@Test
	public void testUnion() {
		redisTemplate.opsForSet().add("testSet1", "value1", "value2", "value3");
		redisTemplate.opsForSet().add("testSet2", "value3", "value4");
		
		redisTemplate.opsForSet().unionAndStore("testSet1", "testSet2", "testSet3");
		MatcherAssert.assertThat(redisTemplate.opsForSet().members("testSet3"), 
				Matchers.containsInAnyOrder("value1", "value2", "value3", "value4"));
	}
	
}
