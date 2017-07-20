package com.qfang.examples.redis.redisson.pool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.redisson.core.RScoredSortedSet;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年3月12日
 * @since 1.0
 */
public class SortedSetTypeTests extends RedissonTestBase{

    @Test
    public void testAddAll() {
        RScoredSortedSet<String> set = redisson.getScoredSortedSet("testAddAll");

        Map<String, Double> objects = new HashMap<String, Double>();
        objects.put("1", 0.1);
        objects.put("2", 0.2);
        objects.put("3", 0.3);
        assertThat(set.addAll(objects)).isEqualTo(3);
    }
	
    @Test
    public void testPollLast() {
        RScoredSortedSet<String> set = redisson.getScoredSortedSet("testPollLast");
        Assert.assertNull(set.pollLast());

        set.add(0.1, "a");
        set.add(0.2, "b");
        set.add(0.3, "c");

        Assert.assertEquals("c", set.pollLast());
        MatcherAssert.assertThat(set, Matchers.contains("a", "b"));
    }
    
    @Test
    public void testRemoveRangeByScore() {
        RScoredSortedSet<String> set = redisson.getScoredSortedSet("testRemoveRangeByScore");
        set.add(0.1, "a");
        set.add(0.2, "b");
        set.add(0.3, "c");
        set.add(0.4, "d");
        set.add(0.5, "e");
        set.add(0.6, "f");
        set.add(0.7, "g");

        Assert.assertEquals(2, set.removeRangeByScore(0.1, false, 0.3, true));
        MatcherAssert.assertThat(set, Matchers.contains("a", "d", "e", "f", "g"));
    }
    
}
