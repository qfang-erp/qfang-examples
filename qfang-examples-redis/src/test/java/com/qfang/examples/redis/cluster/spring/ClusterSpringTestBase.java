package com.qfang.examples.redis.cluster.spring;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ClusterSpringTestBase {
	
	@Resource(name = "jedisClusterConnectionFactory")
	private RedisConnectionFactory jedisClusterConnectionFactory;
	
	protected RedisClusterConnection getConnection() {
		RedisClusterConnection connection = jedisClusterConnectionFactory.getClusterConnection();
		assertThat(connection).isNotNull();
		return connection;
	}
	
	protected void doTest(Executor executor) {
		RedisClusterConnection connection = jedisClusterConnectionFactory.getClusterConnection();
		assertThat(connection).isNotNull();
		executor.execute(connection);
		connection.close();
	}
	
	protected interface Executor {
		void execute(RedisClusterConnection connection);
	}
	
}
