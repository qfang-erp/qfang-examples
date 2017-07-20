package com.qfang.examples.redis.jedis.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public class TransactionTests extends JedisSpringBaseTests {
	
	@Test
	public void testUseTransactionSimple() {
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				operations.multi();
				
				operations.opsForValue().set("test1", "value1");
				operations.opsForSet().add("testSet1", "value1", "value2");
				
				// 这里获取 test1 为 null，因为事务还没有提交
				assertThat(operations.opsForValue().get("test1")).isNull();
				return operations.exec();
			}
		});
		
		assertThat(redisTemplate.opsForValue().get("test1")).isEqualTo("value1");
		MatcherAssert.assertThat(redisTemplate.opsForSet().members("testSet1"), Matchers.containsInAnyOrder("value1", "value2"));
	}
	
	
	@Test
	public void testWatch() throws InterruptedException {
		final String watchKey = "watchKey";
		
		redisTemplate.opsForValue().set(watchKey, "value0");
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				List<Object> result = redisTemplate.execute(new SessionCallback<List<Object>>() {
					@SuppressWarnings({ "rawtypes", "unchecked" })
					@Override
					public List<Object> execute(RedisOperations operations) throws DataAccessException {
						operations.watch(watchKey);  // watch
						
						String oldValue = (String) operations.opsForValue().get(watchKey);
						String newValue = oldValue + "-11";
						
						operations.multi();  // 开启事物
						operations.opsForValue().set(watchKey, newValue);  // 修改值
						
						try {
							Thread.sleep(1000);  // 让子线程暂停下，以便其他线程修改 watchKey 的值
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return operations.exec();  // 提交事物
					}
				});
				
				assertThat(result).isNull();  // 事物提交失败，返回null，否则会返回一个空 List
			}
		});
		t1.start();

		Thread.sleep(100);
		redisTemplate.opsForValue().set(watchKey, "value2");  // 主线程先修改 watchKey 的值，子线程提交时会观察到 watchKey 的值已经改变，所以子线程事物提交会失败
		t1.join();
		
		assertThat(redisTemplate.opsForValue().get(watchKey)).isEqualTo("value2");
	}
	
}
