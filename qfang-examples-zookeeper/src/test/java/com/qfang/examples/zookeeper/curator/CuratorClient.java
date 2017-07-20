package com.qfang.examples.zookeeper.curator;

import com.qfang.examples.zookeeper.Config;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

/**
 * Curator 项目地址： http://curator.apache.org/index.html
 *
 * @author liaozhicheng.cn@163.com
 */
public class CuratorClient {

    @Test
    public void createConnection() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);  // 重试策略
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.ZK_SERVER_CLUSTER, Config.DEFUALT_TIME_OUT, 3000, retryPolicy);
        client.start();
    }

}
