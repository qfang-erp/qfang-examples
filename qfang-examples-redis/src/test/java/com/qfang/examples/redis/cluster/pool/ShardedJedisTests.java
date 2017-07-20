package com.qfang.examples.redis.cluster.pool;

import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年4月24日
 * @since 1.0
 */
public class ShardedJedisTests extends ClusterBaseTests {

	@Test
	public void testConsistentHashing() {
		doTest(new Executor() {
			@Override
			public void execute(ShardedJedis jedis) {
				// 先测试下插入 key1 这个key，发现插入到了 16579 这个节点，后续所有的插入都会在该节点
				jedis.set("key1", "a1");
				JedisShardInfo sj = jedis.getShardInfo("key1");
				Assert.assertEquals(sj.getPort(), 16579);
			}
		});
	}
	
	@Test
	public void testBatchSave() {
		doTest(new Executor() {
			@Override
			public void execute(ShardedJedis jedis) {
				for(int i = 0; i < 100; i++) {
					jedis.set("key"+i, i+"");
				}
			}
		});
	}
	
}
