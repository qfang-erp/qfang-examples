package com.qfang.examples.redis.jedis.spring;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public abstract class JedisSpringBaseTests {
	
	@Resource
	protected RedisTemplate<String, Object> redisTemplate;
	
	
	@Before
	public void flushdb() {
		redisTemplate.getConnectionFactory().getConnection().flushAll();
	}
	
}
