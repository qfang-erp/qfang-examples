package com.qfang.examples.redis.redisson.pool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.redisson.core.RBucket;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年3月8日
 * @since 1.0
 */
public class StringTypeTests extends RedissonTestBase {
	
	@Test
	public void testSetGet() {
		RBucket<String> bucket = redisson.getBucket("test");
		Assert.assertNull(bucket.get());
		String value = "testValue";
		bucket.set(value);
		Assert.assertEquals(value, bucket.get());
	}
	
	@Test
	public void testTrySet() {
		// trySet 采用 SETNX 方式
		RBucket<String> r1 = redisson.getBucket("testTrySet");
		assertThat(r1.trySet("3")).isTrue();
		assertThat(r1.trySet("4")).isFalse();
		assertThat(r1.get()).isEqualTo("3");
	}
	
	@Test
	public void testTimeOut() throws InterruptedException {
		RBucket<String> r1 = redisson.getBucket("testTimeOut");
		r1.trySet("3", 500, TimeUnit.MILLISECONDS);
        assertThat(r1.get()).isEqualTo("3");

        Thread.sleep(500);

        assertThat(r1.get()).isNull();
	}
	
    @Test
    public void testRename() {
        RBucket<String> bucket = redisson.getBucket("test");
        bucket.set("someValue");
        bucket.rename("test1");
        RBucket<String> oldBucket = redisson.getBucket("test");
        Assert.assertNull(oldBucket.get());
        RBucket<String> newBucket = redisson.getBucket("test1");
        Assert.assertEquals("someValue", newBucket.get());
    }
	
    @Test
    public void testSaveBuckets() {
        Map<String, Integer> buckets = new HashMap<String, Integer>();
        buckets.put("12", 1);
        buckets.put("41", 2);
        redisson.saveBuckets(buckets);

        RBucket<Object> r1 = redisson.getBucket("12");
        assertThat(r1.get()).isEqualTo(1);

        RBucket<Object> r2 = redisson.getBucket("41");
        assertThat(r2.get()).isEqualTo(2);
    }
    
}
