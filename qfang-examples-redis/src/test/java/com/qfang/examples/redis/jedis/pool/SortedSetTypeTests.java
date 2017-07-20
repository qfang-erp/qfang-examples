package com.qfang.examples.redis.jedis.pool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年3月10日
 * @since 1.0
 */
public class SortedSetTypeTests extends JedisTestBase {

    @Test
    public void testAddAll() {
        Jedis jedis = getResources();
        Map<String, Double> members = new HashMap<String, Double>();
        members.put("1", 0.1);
        members.put("2", 0.2);
        members.put("3", 0.3);
        members.put("4", 0.3);
        jedis.zadd("testAddAll", members);

        assertThat(jedis.zcount("testAddAll", 0.1, 0.2)).isEqualTo(2);
    }
	
    @Test
    public void testRemrangeByScore() {
    	Jedis jedis = getResources();
        Map<String, Double> members = new HashMap<String, Double>();
        members.put("1", 0.1);
        members.put("2", 0.2);
        members.put("3", 0.3);
        members.put("4", 0.3);
        jedis.zadd("testRemrangeByScore", members);
    	
        jedis.zremrangeByScore("testRemrangeByScore", 0.1, 0.2);
        MatcherAssert.assertThat(jedis.zrange("testRemrangeByScore", 0, -1), Matchers.containsInAnyOrder("3", "4"));
    }
    
    @Test
    public void testZrangebyscore() {
    	Jedis jedis = getResources();
        Map<String, Double> members = new HashMap<String, Double>();
        members.put("1", 0.1);
        members.put("2", 0.2);
        members.put("3", 0.3);
        members.put("4", 0.3);
        jedis.zadd("testZrangebyscore", members);
        
        MatcherAssert.assertThat(jedis.zrangeByScore("testZrangebyscore", 0.1, 0.2), Matchers.containsInAnyOrder("1", "2"));
    }
    
}
