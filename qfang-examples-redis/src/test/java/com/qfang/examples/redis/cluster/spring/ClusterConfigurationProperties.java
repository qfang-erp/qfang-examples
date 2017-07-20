package com.qfang.examples.redis.cluster.spring;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
@Component
@ConfigurationProperties(value= "classpath:redis.properties", prefix = "spring.redis.cluster")
public class ClusterConfigurationProperties {
	
	/*
     * spring.redis.cluster.nodes[0] = 127.0.0.1:7379
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7380
     * ...
     */
	private List<String> nodes;
	
	/**
	 * Get initial collection of known cluster nodes in format {@code host:port}.
	 *
	 * @return
	 */
	public List<String> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
	
}
