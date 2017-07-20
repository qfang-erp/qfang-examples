package com.qfang.examples.zookeeper.zk;

import com.qfang.examples.zookeeper.BaseTests;
import com.qfang.examples.zookeeper.zk.support.CommonWatcher;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

/**
 * 使用 zk 客户端删除节点
 * 1、使用 version 参数可以实现 CAS 删除操作
 * 2、创建节点和修改节点的值都会导致节点的 version 自增，默认是 0 开始，删除节点时 version = -1 表示不关心节点 version
 * 3、zk 只能删除子节点（任何存在子节点的节点都不能删除），而且不支持递归删除
 * 4、zk 支持同步和异步两种方式删除节点
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkDeleteNodeTests extends BaseTests {


//    public static void main(String[] args) throws Exception {
//        initZookeeper();
//
//        String path = "/zk_delete_node";
//        createEphemeralNode(path);
//
//        // 使用 version 进行删除，可以实现 CAS 操作，只有当 version 值正确的时候才会真正删除
//        Stat stat = zooKeeper.exists(path, watcher);
//        System.out.println(stat);
//        deleteNodeSync(path, stat.getVersion());
//
//        // 使用错误的 version 值删除
//        createEphemeralNode(path);
//        try {
//            deleteNodeSync(path, 100);  // 抛出异常：KeeperErrorCode = BadVersion for /zk_delete_node
//        } catch (KeeperException exception) {
//            // ignore
//            System.out.println("catch exception : " + exception.getMessage());
//        }
//
//        // zookeeper 的 version 默认是从 0 开始增加，-1 表示不关心 version 值，任意版本节点都会被删除
//        zooKeeper.exists(path, watcher);
//        deleteNodeSync(path, -1);
//
//        // zookeeper 只能删除叶子节点，如果节点下还有子节点，则删除失败
//        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  // 创建永久节点
//        zooKeeper.exists(path, watcher);
//
//        String childrenPath = path + "/children";
//        createEphemeralNode(childrenPath);
//        try {
//            deleteNodeSync(path, -1);  // 抛出异常: KeeperErrorCode = Directory not empty for /zk_delete_node
//        } catch (Exception exception) {
//            // ignore
//            System.out.println("catch exception : " + exception.getMessage());
//        }
//
//        // 异步方式删除节点
//        deleteNodeAsync(childrenPath, -1);
//        deleteNodeAsync(path, -1);
//
//        Thread.sleep(1000);
//        zooKeeper.close();
//    }

    @Test
    public void deleteSimpleTest() throws KeeperException, InterruptedException {
        String path = "/zk_delete_node";
        createEphemeralNode(path);
        deleteNodeSync(path, -1);

        Stat stat = zooKeeper.exists(path, false);
        System.out.println(stat);
    }

    @Test
    public void deleteWithVersionTest() throws KeeperException, InterruptedException {
        String path = "/zk_delete_node";
        createEphemeralNode(path);

        Stat stat = zooKeeper.exists(path, true);
        deleteNodeSync(path, stat.getVersion());
    }

    @Test(expected = KeeperException.class)
    public void deleteWithErrorVersionTest() throws KeeperException, InterruptedException {
        String path = "/zk_delete_node";
        createEphemeralNode(path);

        // 使用错误的 version 值删除节点
        deleteNodeSync(path, 100);  //  KeeperErrorCode = BadVersion for /zk_delete_node
    }

    @Test(expected = KeeperException.class)
    public void deleteNotChildrenNodeTest() throws KeeperException, InterruptedException {
        final CommonWatcher watcher = new CommonWatcher();
        String path = "/zk_delete_node";
        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  // 创建永久节点
        zooKeeper.exists(path, watcher);

        String childrenPath = path + "/children";
        createEphemeralNode(childrenPath);

        try {
            // 删除一个非叶子节点
            deleteNodeSync(path, -1);  // 抛出异常: KeeperErrorCode = Directory not empty for /zk_delete_node
        } finally {
            deleteNodeSync(childrenPath, -1);
            deleteNodeSync(path, -1);
        }
    }

    @Test
    public void deleteAsync() throws KeeperException, InterruptedException {
        String path = "/zk_delete_node";
        createEphemeralNode(path);

        Stat stat = zooKeeper.exists(path, true);
        zooKeeper.delete(path, stat.getVersion(), new MyVoidCallback(), "i am context");
    }


    static class MyVoidCallback implements AsyncCallback.VoidCallback {

        @Override
        public void processResult(int rc, String path, Object ctx) {
            System.out.println("delete node, result code : " + rc + ", path : " + path + ", context : " + ctx);
        }
    }

}
