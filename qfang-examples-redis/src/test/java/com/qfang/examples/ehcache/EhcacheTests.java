package com.qfang.examples.ehcache;

import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月18日
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class EhcacheTests {
	
	@Autowired
	private Ehcache ehcache;
	
	@Test
	@SuppressWarnings("unchecked")
	public void setTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		
		String cacheKey = "cacheKey";
		ehcache.put(new Element(cacheKey, map));
		
		// Ehcache 存进去的对象和取出来的对象居然是同一个对象，对象的引用都是同一个
		// 这样容易产生一个问题就是在多线程环境下，比如线程A往 ehcache 中存入了一个 ArrayList，并且不断往list中插入元素
		// 然后其他线程去读取该 list 并迭代，这种情况下很容易出现 ConcurrentModificationException
		// 例如：批量计算时，计算线程往ehcache中插入了list，并且在计算过程中碰到校验错误信息后再更新list，然后其他读线程同时读这个缓存返回给页面，很容易出现该异常
		Map<String, String> cacheMap = (Map<String, String>) ehcache.get(cacheKey).getObjectValue();
		Map<String, String> cacheMap2 = (Map<String, String>) ehcache.get(cacheKey).getObjectValue();
		Assert.assertTrue(cacheMap == map);
		Assert.assertTrue(cacheMap == cacheMap2);
	}

}
