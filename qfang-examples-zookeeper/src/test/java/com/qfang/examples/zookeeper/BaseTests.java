package com.qfang.examples.zookeeper;

import com.qfang.examples.zookeeper.zk.support.CommonWatcher;
import com.qfang.examples.zookeeper.zk.support.CurrentNodes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.Optional;

/**
 * zk 基础测试类
 *
 * @author liaozhicheng.cn@163.com
 */
public class BaseTests {

    protected ZooKeeper zooKeeper;

    @Before
    public void setUp() throws IOException, InterruptedException {
        CurrentNodes.init();

        CommonWatcher watcher = new CommonWatcher();
        zooKeeper = new ZooKeeper(Config.ZK_SERVER_CLUSTER, Config.DEFUALT_TIME_OUT, watcher);
        watcher.await();
    }

    @After
    public void close() throws InterruptedException, KeeperException {
        while(!CurrentNodes.isEmpty()) {
            Optional<String> path = CurrentNodes.pop();
            if(path.isPresent()) {
                Stat stat = zooKeeper.exists(path.get(), false);
                if(stat != null) {
                    zooKeeper.delete(path.get(), -1);
                }
            }
        }
        zooKeeper.close();
    }

    protected ZooKeeper newZookeeper() throws IOException, InterruptedException {
        CommonWatcher watcher = new CommonWatcher();
        ZooKeeper zooKeeper = new ZooKeeper(Config.ZK_SERVER_CLUSTER, Config.DEFUALT_TIME_OUT, watcher);
        watcher.await();
        return zooKeeper;
    }

    protected String createEphemeralNode(String path) throws KeeperException, InterruptedException {
        return createEphemeralNode(path, Config.DEFAULT_NODE_DATA);
    }

    protected String createEphemeralNode(String path, byte[] data) throws KeeperException, InterruptedException {
        return createNode(zooKeeper, path, data, CreateMode.EPHEMERAL);
    }

    protected String createPersistentNode(String path) throws KeeperException, InterruptedException {
        return createPersistentNode(path, Config.DEFAULT_NODE_DATA);
    }

    protected String createPersistentNode(String path, byte[] data) throws KeeperException, InterruptedException {
        return createNode(zooKeeper, path, data, CreateMode.PERSISTENT);
    }

    protected String createNode(ZooKeeper zooKeeper, String path, byte[] data, CreateMode createMode) throws KeeperException, InterruptedException {
        String newPath = zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
        CurrentNodes.put(newPath);
        return newPath;
    }

    protected void deleteNodeSync(String path, int version) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, version);
    }


}
