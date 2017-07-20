package com.qfang.examples.redis.cluster.pool;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.qfang.examples.redis.TestConfig;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年4月24日
 * @since 1.0
 */
public class ClusterBaseTests {
	
	protected static ShardedJedisPool jedisPool;
	
	@BeforeClass
	public static void initialize() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo shard1 = new JedisShardInfo(TestConfig.HOST_NAME, 16379);
		shards.add(shard1);
		
		JedisShardInfo shard2 = new JedisShardInfo(TestConfig.HOST_NAME, 16479);
		shards.add(shard2);
		
		JedisShardInfo shard3 = new JedisShardInfo(TestConfig.HOST_NAME, 16579);
		shards.add(shard3);
		
		jedisPool = new ShardedJedisPool(new GenericObjectPoolConfig(), shards);
	}
	
	
	protected void doTest(Executor executor) {
		ShardedJedis jedis = jedisPool.getResource();
		executor.execute(jedis);
		jedis.close();
	}
	
	
	@AfterClass
	public static void destory() {
		if(jedisPool != null) {
			jedisPool.destroy();
			jedisPool.close();
		}
	}
	
	protected interface Executor {
		void execute(ShardedJedis jedis);
	}

}
