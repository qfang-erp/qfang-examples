package com.qfang.examples.zookeeper.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.qfang.examples.zookeeper.Config;
import com.qfang.examples.zookeeper.zk.support.CommonWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 * ZooKeeper 连接创建简单示例
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkConnectTests {
	
    @Test
    public void connectionSimple() throws IOException, InterruptedException {
    	final CommonWatcher watch = new CommonWatcher();
    	
    	ZooKeeper zooKeeper = new ZooKeeper(Config.ZK_SERVER_CLUSTER, 5000, watch);
        // 因为 zookeeper 的连接是异步的，所以这里的连接还没有完成
        System.out.println(zooKeeper.getState());  // CONNECTING
        
        watch.await();  // 这里阻塞等待，连接创建完成之后会收到通知事件

        // 这里连接已经创建好了
        System.out.println(zooKeeper.getState());  // CONNECTED

        System.out.println("Zookeeper session established.");
    }
    
    @Test
    public void connectionUseSID() throws IOException, InterruptedException {
    	final CountDownLatch latch = new CountDownLatch(1);
    	final Watcher watch = new MyWatcher(latch);
    	
    	ZooKeeper zooKeeper = new ZooKeeper(Config.ZK_SERVER_CLUSTER, 5000, watch);
    	latch.await();

        long sessionId = zooKeeper.getSessionId();
        byte[] passwd = zooKeeper.getSessionPasswd();

        // 使用错误的 sessionId 和 password 连接，客户端将收到 Expired 事件通知
        zooKeeper = new ZooKeeper(Config.ZK_SERVER, 5000, watch, 1l, "test".getBytes());
        zooKeeper.getState();

        // 使用之前正确的 sessionId 和 password 来创建连接
        zooKeeper = new ZooKeeper(Config.ZK_SERVER, 5000, watch, sessionId, passwd);
        zooKeeper.getState();

        Thread.sleep(1000);
    }
    
    private class MyWatcher implements Watcher {
    	private final CountDownLatch latch;

    	private MyWatcher(CountDownLatch latch) {
            this.latch = latch;
        }
    	
    	@Override
        public void process(WatchedEvent event) {
            System.out.println("Received watched event: " + event);
            if(Event.KeeperState.SyncConnected == event.getState()) {
                latch.countDown();
            }
        }
    }
    
}
