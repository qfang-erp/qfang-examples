package com.qfang.examples.redis.cluster.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
//@Configuration
public class ClusterAppConfig {
	
	@Autowired
	private ClusterConfigurationProperties clusterProperties;
	
	@Bean
	public RedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory(
				new RedisClusterConfiguration(clusterProperties.getNodes()));
	}
	
}
