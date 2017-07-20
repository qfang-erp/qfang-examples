package com.qfang.examples.redis.cluster.pool;

import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import redis.clients.jedis.ShardedJedis;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年4月24日
 * @since 1.0
 */
public class SetTypeTests extends ClusterBaseTests {

	@Test
	public void testSetGet() {
		doTest(new Executor() {
			@Override
			public void execute(ShardedJedis jedis) {
				jedis.sadd("testSet", "value1", "value2", "value3");
				
				MatcherAssert.assertThat(jedis.smembers("testSet"), Matchers.containsInAnyOrder("value1", "value2", "value3"));
			}
		});
	}
	
	@Test
	public void testIsMember() {
		doTest(new Executor() {
			@Override
			public void execute(ShardedJedis jedis) {
				jedis.sadd("testIsMember", "value1", "value2", "value3", "value1");
				
				assertThat(jedis.sismember("testIsMember", "value2")).isTrue();
			}
		});
	}
	
}
