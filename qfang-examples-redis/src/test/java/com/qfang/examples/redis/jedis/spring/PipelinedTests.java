package com.qfang.examples.redis.jedis.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public class PipelinedTests extends JedisSpringBaseTests {
	
	@Test
	public void testUsePipelined() {
		redisTemplate.opsForValue().set("testUsePipelined", "testValue");
		redisTemplate.opsForSet().add("testUsePipelinedSet", "test1", "test2");
		
		MatcherAssert.assertThat(redisTemplate.opsForSet().members("testUsePipelinedSet"), 
				Matchers.containsInAnyOrder("test1", "test2"));
		
		// 获取系统默认采用 key 的序列化工具类
		@SuppressWarnings("unchecked")
		final RedisSerializer<String> serializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
		redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				
				// 注意这里如果直接使用 "test".getBytes() 将无法正常删除掉 key，因为 key 的序列化方式不一样
				// connection.del("test".getBytes());
				connection.del(serializer.serialize("testUsePipelined"));
				connection.del(serializer.serialize("testUsePipelinedSet"));
				connection.closePipeline();
				return null;
			}
		});
		
		assertThat(redisTemplate.opsForValue().get("testUsePipelined")).isNull();
	}
	
}
