package com.qfang.examples.akka.mcache.remote.client;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-11-04
 * @since: 1.0
 */
public class JClientIntegrationTest {

    private JClient client = new JClient("127.0.0.1:2552");

    @Test
    public void clientMain() throws Exception {
        client.set("key123", "value123");
        String result = (String) ((CompletableFuture) client.get("key123")).get();
        Assert.assertEquals(result, "value123");
    }

}
