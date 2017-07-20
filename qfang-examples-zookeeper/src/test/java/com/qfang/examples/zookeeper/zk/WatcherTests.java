package com.qfang.examples.zookeeper.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.qfang.examples.zookeeper.Config;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 * Watcher
 * 1、创建 Zookeeper 对象时，可以传一个 watcher 对象作为默认的 watcher
 * 2、每次注册的 watcher 只能使用一次，接收一次事件之后该 watcher 就失效了，如果需要反复监听，就需要重新注册
 * 
 * @author liaozhicheng.cn@163.com
 */
public class WatcherTests {

	@Test
	public void watcherSimpleTest() throws IOException, KeeperException, InterruptedException {
	    CountDownLatch latch = new CountDownLatch(1);
        Watcher watcher = new SimpleWatcher(latch);
		ZooKeeper zk = new ZooKeeper(Config.ZK_SERVER, 5000, watcher);

        // 删除节点，但是 watcher 中并不会收到节点删除的通知，因为 watcher 这时已经失效了
        String path = "/zk_examples";
		zk.create(path, "init".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.delete(path, -1);

        String path2 = "/zk_examples_delete";
        zk.create(path2, "init".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.exists(path2, watcher);  // 重新注册 watcher
        zk.delete(path2, -1);
	}

	
	private static class SimpleWatcher implements Watcher {

	    private final CountDownLatch latch;

        SimpleWatcher(CountDownLatch latch) {
            this.latch = latch;
        }

		@Override
		public void process(WatchedEvent event) {
            if(Event.KeeperState.SyncConnected == event.getState()) {
                if(Event.EventType.None == event.getType() && event.getPath() == null) {
                    latch.countDown();
                    System.out.println("connection init");
                } else if(Event.EventType.NodeDeleted == event.getType()) {
                    System.out.println("node delete, path : " + event.getPath());
                }
            }
		}
		
	}
	
}

