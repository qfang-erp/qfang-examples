package com.qfang.examples.zookeeper.zk;

import com.qfang.examples.zookeeper.BaseTests;
import com.qfang.examples.zookeeper.Config;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.List;

/**
 * zk 子节点相关操作
 *
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkChildrensTests extends BaseTests {

    @Test
    public void getChildrensTest() throws KeeperException, InterruptedException {
        createPersistentNode(Config.DEFAULT_TEST_PATH);

        // 创建10个子节点
        for(int i = 0; i < 10; i++) {
            // 注：子节点变更事件的 watcher 只能通过 getChildren 方式来注册，使用 exists 方式注册的 watcher 不会接收子节点变更通知
            zooKeeper.getChildren(Config.DEFAULT_TEST_PATH, true);  // 因为 watcher 使用一次后就失效了，所以这里需要重复注册
            createEphemeralNode(Config.DEFAULT_TEST_PATH + "/" + i);
        }

        // 注：这里返回的节点路径只是子节点的相对路径，而不是包含父节点的绝对路径
        List<String> childrenList = zooKeeper.getChildren(Config.DEFAULT_TEST_PATH, true);
        System.out.println(childrenList.toString()); // [3, 2, 1, 0, 7, 6, 5, 4, 9, 8]
    }

    @Test
    public void getChildrensAsyncTest() throws KeeperException, InterruptedException {
        createPersistentNode(Config.DEFAULT_TEST_PATH);
        createEphemeralNode(Config.DEFAULT_TEST_PATH + "/children");

        zooKeeper.getChildren(Config.DEFAULT_TEST_PATH, true, new MyChildrenCallback(), null);
    }

    private class MyChildrenCallback implements AsyncCallback.Children2Callback {
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            System.out.println(children);
        }
    }


}
