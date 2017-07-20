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
public class ListTypeTests extends ClusterBaseTests {

	@Test
	public void testLSetGet() {
		// 测试新增和获取
		doTest(new Executor() {
			@Override
			public void execute(ShardedJedis jedis) {
				String key = "testLset";
				jedis.lpush(key, "test0", "test1");
				jedis.rpush(key, "test2");
				MatcherAssert.assertThat(jedis.lrange(key, 0, -1), Matchers.contains("test1", "test0", "test2"));
			}
		});
	}
	
	@Test
    public void testAddByIndex() {
		doTest(new Executor() {
			@Override
			public void execute(ShardedJedis jedis) {
				jedis.lpush("testAddByIndex", "value1");
				jedis.lpush("testAddByIndex", "value2");
				jedis.rpush("testAddByIndex", "value3");
		        MatcherAssert.assertThat(jedis.lrange("testAddByIndex", 0, -1), Matchers.contains("value2", "value1", "value3"));
			}
		});
    }

	@Test
	public void testLength() {
		doTest(new Executor() {
			@Override
			public void execute(ShardedJedis jedis) {
				for(int i = 0; i < 10; i++) {
					jedis.rpush("testLength", i + "");
				}
				
				assertThat(jedis.llen("testLength")).isEqualTo(10);
				assertThat(jedis.lindex("testLength", 2)).isEqualTo("2");
			}
		});
	}
	
}
