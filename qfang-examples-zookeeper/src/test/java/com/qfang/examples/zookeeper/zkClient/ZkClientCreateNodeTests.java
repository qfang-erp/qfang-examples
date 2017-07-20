package com.qfang.examples.zookeeper.zkClient;

import com.qfang.examples.zookeeper.Config;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author liaozhicheng.cn@163.com
 */
public class ZkClientCreateNodeTests {

    public void createNode() {
        ZkClient zkClient = new ZkClient(Config.ZK_SERVER_CLUSTER, Config.DEFUALT_TIME_OUT);
        zkClient.createEphemeral(Config.DEFAULT_TEST_PATH, Config.DEFAULT_NODE_DATA);
    }

}
