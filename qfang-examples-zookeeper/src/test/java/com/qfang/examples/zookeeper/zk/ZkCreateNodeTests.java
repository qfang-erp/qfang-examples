package com.qfang.examples.zookeeper.zk;

import com.qfang.examples.zookeeper.zk.support.CommonWatcher;
import org.apache.zookeeper.*;
import org.junit.Test;

import static com.qfang.examples.zookeeper.Config.DEFUALT_TIME_OUT;
import static com.qfang.examples.zookeeper.Config.ZK_SERVER_CLUSTER;

/**
 * ZNode 节点创建
 * 1、zk 支持2种节点，持久节点和临时节点，但是每种节点类型又可以是普通/顺序节点，即总共有4中节点类型（持久，持久顺序，临时，临时顺序）
 * 2、zk 节点的数据只支持 byte[] 类型，客户端必须自己负责数据的序列化和反序列化
 * 3、异步创建节点，回调函数第一个参数 rc（ResultCode）表示服务端响应码
 *   0：表示创建成果
 *   -4：客户端和服务端链接已断开
 *   -110：节点已存在
 *   -112：会话已过期
 * 4、zk 临时节点下不能创建子节点，否则抛出 KeeperErrorCode = NoChildrenForEphemerals for /zk_delete_node/children
 *
 * 注意：默认情况下 zk 的节点不能递归创建，即客户端必须先创建父节点，然后再创建子节点
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkCreateNodeTests {

    @Test
    public void createNodeSyncTest() throws Exception {
        final CommonWatcher watcher = new CommonWatcher();
        ZooKeeper zooKeeper = new ZooKeeper(ZK_SERVER_CLUSTER, DEFUALT_TIME_OUT, watcher);
        watcher.await();

        // 创建临时节点，临时节点只在当前会发范围内有效，当会话结束时，该节点将不能访问
        String path = zooKeeper.create("/zookeeper-test", "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create node : " + path);

        // 临时节点下不能有子节点
        String childrenPath = path + "/children";
        try {
            zooKeeper.create(childrenPath, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException exception) {
            // ignore
            System.out.println("catch exception : " + exception.getMessage());
        }

        // 临时顺序节点 /zookeeper-test0000000004 zk 会自动生成一个全局唯一的数字
        String path2 = zooKeeper.create("/zookeeper-test", "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create node : " + path2);

        // 这里需要调用 close 来结束当前会话，这样 zookeeper 服务端才能及时删除临时节点
        zooKeeper.close();
    }


    @Test
    public void createNodeAsyncTest() throws Exception {
        final CommonWatcher watcher = new CommonWatcher();
        ZooKeeper zooKeeper = new ZooKeeper(ZK_SERVER_CLUSTER, DEFUALT_TIME_OUT, watcher);
        watcher.await();

        String path = "/zk_node_create_async";
        // 异步的方式创建节点，节点创建成功之后会调用 MyStringCallback 中的回调方法
        // create path : /zk_node_create_async, result code : 0, context : I am context, real path name : /zk_node_create_async
        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new MyStringCallback(), "I am context");

        // 创建同样的节点，此时节点已经存在, Watcher 中回调输出
        // create path : /zk_node_create_async, result code : -110, context : I am context, real path name : null
        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new MyStringCallback(), "I am context");

        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new MyStringCallback(), "I am context");

        Thread.sleep(1000);
        zooKeeper.close();

    }


    static class MyStringCallback implements AsyncCallback.StringCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("create path : " + path + ", result code : " + rc + ", context : " + ctx + ", real path name : " + name);
        }
    }

}
