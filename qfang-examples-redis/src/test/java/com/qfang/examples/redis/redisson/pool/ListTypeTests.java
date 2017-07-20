package com.qfang.examples.redis.redisson.pool;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.redisson.core.RList;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年3月11日
 * @since 1.0
 */
public class ListTypeTests extends RedissonTestBase {

	@Test
	public void testSetGet() {
		RList<String> list1 = redisson.getList("list1");
        list1.add("1");
        list1.add("2");
        list1.add("3");

        RList<String> list2 = redisson.getList("list2");
        list2.add("1");
        list2.add("2");
        list2.add("3");
        
        Assert.assertEquals(list1, list2);
        MatcherAssert.assertThat(redisson.getList("list1"), Matchers.contains("1", "2", "3"));
	}
	
	@Test
    public void testAddByIndex() {
        RList<String> list = redisson.getList("testAddByIndex");
        list.add("value1");
        list.add(0, "value2");

        MatcherAssert.assertThat(list, Matchers.contains("value2", "value1"));
    }
	
    @Test
    public void testListIteratorGetSet() {
        List<Integer> list = redisson.getList("list");
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        ListIterator<Integer> iterator = list.listIterator();

        Assert.assertFalse(iterator.hasPrevious());
        Assert.assertTrue(1 == iterator.next());
        iterator.set(3);  // set 将第一个元素替换成 3
        Assert.assertThat(list, Matchers.contains(3, 2, 3, 4));
        Assert.assertTrue(2 == iterator.next());
        iterator.add(31);  // add 在当前 iterator 索引后面添加元素
        Assert.assertThat(list, Matchers.contains(3, 2, 31, 3, 4));
        Assert.assertTrue(3 == iterator.next());
        Assert.assertTrue(4 == iterator.next());
        Assert.assertFalse(iterator.hasNext());
        iterator.add(71);
        Assert.assertThat(list, Matchers.contains(3, 2, 31, 3, 4, 71));
        list.add(8);  // list#add 在list最后面添加元素
        Assert.assertThat(list, Matchers.contains(3, 2, 31, 3, 4, 71, 8));
        
        list.removeAll(Arrays.asList(2, 3, 4));
        Assert.assertThat(list, Matchers.contains(31, 71, 8));
    }
}
