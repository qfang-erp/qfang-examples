package com.qfang.examples.redis.codis;

import org.junit.Test;

import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;

import redis.clients.jedis.Jedis;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月2日
 * @since 1.0
 */
public class CodisTestBase {
	
	@Test
	public void baseTest() {
		JedisResourcePool jedisPool = RoundRobinJedisPool.create()
				.curatorClient("192.168.1.161:2181", 30000)
				.zkProxyDir("/zk/codis/db_test/proxy")
				.build();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set("foo", "bar");
			String value = jedis.get("foo");
			System.out.println(value);
		}
	}
	
}
