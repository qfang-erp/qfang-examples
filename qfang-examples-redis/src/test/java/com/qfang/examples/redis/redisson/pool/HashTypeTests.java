package com.qfang.examples.redis.redisson.pool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.redisson.core.RMap;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年3月11日
 * @since 1.0
 */
public class HashTypeTests extends RedissonTestBase {

	@Test
	public void testSetGet() {
		RMap<String, String> map = redisson.getMap("testMap");
		map.put("field1", "value1");
		map.put("field2", "value2");
		
		assertThat(map.containsKey("field1")).isTrue();
		map.putIfAbsent("field2", "value2New");  // 不能添加成功
		assertThat(map.get("field2")).isEqualTo("value2");
	}
	
    @Test
    public void testGetAll() {
        RMap<Integer, Integer> map = redisson.getMap("getAll");
        map.put(1, 100);
        map.put(2, 200);
        map.put(3, 300);
        map.put(4, 400);

        Map<Integer, Integer> filtered = map.getAll(new HashSet<Integer>(Arrays.asList(2, 3, 5)));

        Map<Integer, Integer> expectedMap = new HashMap<Integer, Integer>();
        expectedMap.put(2, 200);
        expectedMap.put(3, 300);
        Assert.assertEquals(expectedMap, filtered);
    }
	
    @Test
    public void testRemove() {
        Map<String, String> map = redisson.getMap("simple");
        map.put("field1", "value1");
        map.put("field2", "value2");
        map.put("field3", "value3");

        map.remove("field3");
        map.remove("field1");

        Assert.assertEquals(1, map.size());
        Assert.assertTrue(map.containsKey("field2"));
    }
	
}
