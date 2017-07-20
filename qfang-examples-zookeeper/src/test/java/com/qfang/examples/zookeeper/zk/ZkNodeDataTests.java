package com.qfang.examples.zookeeper.zk;

import com.qfang.examples.zookeeper.BaseTests;
import com.qfang.examples.zookeeper.Config;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

/**
 * zk 节点数据操作相关
 * 1、获取数据
 * 2、更新数据
 * 3、监听数据的变化
 *
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkNodeDataTests extends BaseTests {

    @Test
    public void getDataTest() throws Exception {
        createEphemeralNode(Config.DEFAULT_TEST_PATH);

        // 节点数据获取之后，这个新建的 stat 对象中的数据会被设置
        Stat stat = new Stat();
        byte[] datas = zooKeeper.getData(Config.DEFAULT_TEST_PATH, true, stat);
        String data = new String(datas, Config.UTF_8);
        System.out.println(data);
        System.out.println(stat);
    }

    @Test
    public void updateDataTest() throws Exception {
        createEphemeralNode(Config.DEFAULT_TEST_PATH);

        Stat stat = new Stat();
        byte[] datas = zooKeeper.getData(Config.DEFAULT_TEST_PATH, true, stat);  // getData 的同时注册 watcher
        System.out.println("init data : " + new String(datas, Config.UTF_8));

        zooKeeper.setData(Config.DEFAULT_TEST_PATH, "update data".getBytes(Config.UTF_8_CHARSET), -1);
        byte[] newDatas = zooKeeper.getData(Config.DEFAULT_TEST_PATH, true, stat);
        System.out.println("new data : " + new String(newDatas, Config.UTF_8));
    }

    @Test(expected = KeeperException.class)
    public void updateDataWithVersionTest() throws Exception {
        createEphemeralNode(Config.DEFAULT_TEST_PATH);

        Stat stat = new Stat();
        byte[] datas = zooKeeper.getData(Config.DEFAULT_TEST_PATH, true, stat);  // getData 的同时注册 watcher
        System.out.println("init data : " + new String(datas, Config.UTF_8));

        // 使用错误的 version 进行数据更新，更新失败（利用该特性可以实现 zk 数据的 CAS 更新）
        // KeeperErrorCode = BadVersion for /zk_examples
        zooKeeper.setData(Config.DEFAULT_TEST_PATH, "update data".getBytes(Config.UTF_8_CHARSET), 100);
    }

    @Test
    public void casUpdateTest() throws Exception {
        createEphemeralNode(Config.DEFAULT_TEST_PATH);

        final Stat stat = new Stat();
        byte[] datas = zooKeeper.getData(Config.DEFAULT_TEST_PATH, true, stat);  // getData 的同时注册 watcher
        System.out.println("init data : " + new String(datas, Config.UTF_8));

        // 模拟多个线程同时更新一个节点的值，这里只会有一个线程能成功更新，其他线程都会失败
        // 因为数据被更新之后节点的 version 值就会发生变化
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    zooKeeper.setData(Config.DEFAULT_TEST_PATH, "update data".getBytes(Config.UTF_8_CHARSET), stat.getVersion());
                    System.out.println("update success. " + Thread.currentThread().getName());
                } catch (KeeperException e) {
                    // ignore
                    System.out.println("update failed. " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(1000);
    }

}
