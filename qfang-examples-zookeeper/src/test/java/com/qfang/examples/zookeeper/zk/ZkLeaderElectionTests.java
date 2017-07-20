package com.qfang.examples.zookeeper.zk;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * 作业1： （上传代码到github中）
 * 用Zookeeper原生提供的SDK实现“先到先得——公平模式”的Leader Election。即各参与方都注册ephemeral_sequential节点，ID较小者为leader。
 * 要求：
 * 1. 每个竞选失败的参与方，只能watch前一个
 * 2. 要能处理部分参与方等待过程中失败的情况
 * 3. 实现2个选举方法，一个阻塞直到获得leadership，另一个竞选成功则返回true，否则返回失败，但要能支持回调
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年11月5日
 * @since 1.0
 */
public class ZkLeaderElectionTests {
	
	private static final String ELECTION_ROOT_PATH = "/zk-leaderelection";
	private static final String ZK_CLUSTER_SERVER = "192.168.1.186:2181,192.168.187:2181,192.168.1.188:2181";
	private static final int THREAD_COUNTS = 5;
	
	@Before
	public void setUp() {
		try {
			ZooKeeper zookeeper = doZkConnect();
			Stat stat = zookeeper.exists(ELECTION_ROOT_PATH, false);
			if(stat != null) {
				zookeeper.delete(ELECTION_ROOT_PATH, -1);
			}
			zookeeper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void leaderElectionTest() throws KeeperException, InterruptedException, IOException {
		// 先初始化创建一个永久节点 /zk-leaderelection 用于后面参与 leader election 节点的父节点
		ZooKeeper zooKeeper = doZkConnect();
		zooKeeper.create(ELECTION_ROOT_PATH, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zooKeeper.close();
		
		CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNTS);
		// 这里用5个线程来模拟5个客户端进行 leader election
		List<Thread> clients = IntStream.range(0, THREAD_COUNTS).mapToObj(i -> {
			return new Thread(new Client(barrier), "thread-" + i);
		}).peek(t -> t.start()).collect(toList());
		
		clients.forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private ZooKeeper doZkConnect() throws IOException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		ConnectWatcher connectWatcher = new ConnectWatcher(latch);
		ZooKeeper zooKeeper = new ZooKeeper(ZK_CLUSTER_SERVER, 5000, connectWatcher);
    	latch.await();
    	return zooKeeper;
	}
	
	private String getPrevNodePath(List<String> childrens, String pathWitSeq) {
		// 根据当前节点的 path 获取比自己小的最大的节点 path，如果自己就是最小的节点，则返回 null
		int index = 0;
		List<String> sortedChildrens = childrens.stream().sorted().collect(toList());
		for(int i = 0; i < sortedChildrens.size(); i++) {
			if(pathWitSeq.equals(sortedChildrens.get(i))) {
				index = i;
				break ;
			}
		}
		
		if(index == 0)
			return null; // 表示当前节点就是最小节点
		return sortedChildrens.get(index - 1);  // 否则返回
	}
	
	
	class Client implements Runnable {

		private final Object lock = new Object();
		private final CyclicBarrier barrier;
		
		private ZooKeeper zookeeper;
		private String pathWitSeq;
		
		
		private Client(CyclicBarrier barrier) {
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			try {
				zookeeper = doZkConnect();
				pathWitSeq = zookeeper.create(ELECTION_ROOT_PATH + "/lock", "init".getBytes(), 
						ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				barrier.await();  // 等待所有的客户端都连接完成
				
				elect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void elect() throws KeeperException, InterruptedException {
			List<String> childrens = zookeeper.getChildren(ELECTION_ROOT_PATH, false);  // 这里返回的字节点名称不是绝对路径
			childrens = childrens.stream().map(p -> ELECTION_ROOT_PATH + "/" + p).collect(toList());
			
			String prevNodePath = getPrevNodePath(childrens, pathWitSeq);
			System.out.format("childrens : %s \n -- thread : %s , pathWitSeq : %s, prevNodePath : %s \n", 
					childrens.stream().collect(joining(",")), Thread.currentThread().getName(), pathWitSeq, prevNodePath);
			
			if(prevNodePath == null) {  // 说明没有比自己更小的节点
				System.out.format("Client: %s, time: %s, become new leader... \n", 
						Thread.currentThread().getName(), LocalTime.now().toString());
				
				TimeUnit.SECONDS.sleep(5);  // 成为 leader 后睡眠5s后自动退出
				zookeeper.close();
				
				synchronized (lock) {
					lock.notify();  // 唤醒当前线程，退出（注：watch 事件线程和当前线程不是同一个线程）
				}
			} else {
				zookeeper.exists(prevNodePath, new ElectionWatch(this));
				
				synchronized (lock) {
					lock.wait();  // 不能成为 leader，阻塞当前线程，一直等到上一个节点退出
				}
			}
		}
		
		protected boolean elect(ElectionCallback callback) 
				throws KeeperException, InterruptedException {
			List<String> childrens = zookeeper.getChildren(ELECTION_ROOT_PATH, false);  // 这里返回的字节点名称不是绝对路径
			childrens = childrens.stream().map(p -> ELECTION_ROOT_PATH + "/" + p).collect(toList());
			
			String prevNodePath = getPrevNodePath(childrens, pathWitSeq);
			System.out.format("childrens : %s \n -- thread : %s , pathWitSeq : %s, prevNodePath : %s \n", 
					childrens.stream().collect(joining(",")), Thread.currentThread().getName(), pathWitSeq, prevNodePath);
			
			if(prevNodePath == null) {
				System.out.format("Client: %s, time: %s, become new leader... \n", 
						Thread.currentThread().getName(), LocalTime.now().toString());
				return true;
			} else {
				zookeeper.exists(prevNodePath, new Watcher() {
					@Override
					public void process(WatchedEvent event) {
						if(event.getType() == Event.EventType.NodeDeleted) {
							callback.execute();
						}
					}
				});
				return false;
			}
		}
		
	}
	
	
	private class ConnectWatcher implements Watcher {
    	private final CountDownLatch latch;

    	private ConnectWatcher(CountDownLatch latch) {
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
	
	private class ElectionWatch implements Watcher {
		
		private final Client client;
		
		private ElectionWatch(Client client) {
			this.client = client;
		}

		@Override
		public void process(WatchedEvent event) {
			if(event.getType() == Event.EventType.NodeDeleted) {
				try {
					client.elect();
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private interface ElectionCallback {
		
		void execute();
		
	}
	
}
