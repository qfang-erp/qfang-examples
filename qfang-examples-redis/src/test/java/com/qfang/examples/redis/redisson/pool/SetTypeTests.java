package com.qfang.examples.redis.redisson.pool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.redisson.core.RSet;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年3月11日
 * @since 1.0
 */
public class SetTypeTests extends RedissonTestBase {

	@Test
	public void testSetGet() {
		RSet<String> set = redisson.getSet("testSet");
		set.add("value1");
		set.add("value2");
		set.add("value1");  // 重复元素插入不了

		// 因为 set 是无序的 
		MatcherAssert.assertThat(redisson.getSet("testSet"), Matchers.containsInAnyOrder("value2", "value1"));
        assertThat(set.size()).isEqualTo(2);
	}
	
    @Test
    public void testSize() {
        Set<Integer> set = redisson.getSet("set");
        for(int i = 0; i < 10; i++) {
        	set.add(i);
        }
        Assert.assertEquals(10, set.size());
    }
	
    @Test
    public void testRemoveRandom() {
        RSet<Integer> set = redisson.getSet("testSet");
        set.add(1);
        set.add(2);
        set.add(3);
        
        // removeRandom -> 采用的是 SPOP 命令
        MatcherAssert.assertThat(set.removeRandom(), Matchers.isOneOf(1, 2, 3));
        MatcherAssert.assertThat(set.removeRandom(), Matchers.isOneOf(1, 2, 3));
        MatcherAssert.assertThat(set.removeRandom(), Matchers.isOneOf(1, 2, 3));
        Assert.assertNull(set.removeRandom());
    }
    
    @Test
    public void testUnion() {
    	// 0, 1, 2, 3, 4
    	RSet<Integer> set1 = redisson.getSet("set1");
    	for(int i = 0; i < 5; i++) {
    		set1.add(i);
    	}
    	// 2, 3, 4, 5, 6, 7
    	RSet<Integer> set2 = redisson.getSet("set2");
    	for(int i = 2; i < 8; i++) {
    		set2.add(i);
    	}
    	// 0, 1, 2, 3, 4, 5, 6, 7 
    	RSet<Integer> set3 = redisson.getSet("set3");
    	set3.union("set1", "set2");
    	Assert.assertEquals(8, set3.size());
    }
    
}
