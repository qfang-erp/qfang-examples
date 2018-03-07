package com.qfang.examples.akka.mcache.remote.server;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.qfang.examples.akka.mcache.MCacheDb;
import org.junit.Test;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-11-04
 * @since: 1.0
 */
public class MCacheDbServerTest {

    @Test
    public void testMain() throws InterruptedException {
        ActorSystem system = ActorSystem.create("akkademy");
        system.actorOf(Props.create(MCacheDb.class), "mCacheDb");

        synchronized (this) {
            this.wait();
        }
    }

}
