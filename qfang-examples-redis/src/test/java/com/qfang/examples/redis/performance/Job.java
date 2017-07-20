package com.qfang.examples.redis.performance;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public interface Job {
	
	void execute(long index, String threadName);
	
}
