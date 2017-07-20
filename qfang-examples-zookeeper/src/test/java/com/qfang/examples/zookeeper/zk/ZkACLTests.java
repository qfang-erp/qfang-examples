package com.qfang.examples.zookeeper.zk;

import com.qfang.examples.zookeeper.BaseTests;
import com.qfang.examples.zookeeper.Config;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;

/**
 * zk 权限控制相关
 *
 * zookeeper 提供了多种权限控制模式，包括: world, auth, digest, ip, super
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkACLTests extends BaseTests {

    @Test
    public void addAuthSimpleTest() throws KeeperException, InterruptedException, IOException {
        // 采用 digest 权限控制模式，foo:true 类似 username:password
        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
        createEphemeralNode(Config.DEFAULT_TEST_PATH);

        // 这里会输出 no auth exception
        ZooKeeper zooKeeper1 = newZookeeper();
        try {
            zooKeeper1.getData(Config.DEFAULT_TEST_PATH, false, null);
        } catch (Exception e) {
            // ignore
        }

        ZooKeeper zooKeeper2 = newZookeeper();
        zooKeeper2.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper2.getData(Config.DEFAULT_TEST_PATH, false, null);
    }

    @Test
    public void deleteAuthNodeTest() {

    }

}
