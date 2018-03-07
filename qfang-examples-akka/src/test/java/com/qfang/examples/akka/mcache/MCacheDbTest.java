package com.qfang.examples.akka.mcache;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.qfang.examples.akka.mcache.message.SetRequest;
import org.junit.Assert;
import org.junit.Test;

import static scala.compat.java8.FutureConverters.*;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-11-04
 * @since: 1.0
 */
public class MCacheDbTest {

    ActorSystem actorSystem = ActorSystem.create("mCacheDb");

    @Test
    public void testPut() {
        TestActorRef<MCacheDb> actorRef = TestActorRef.create(actorSystem, Props.create(MCacheDb.class));
        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());

        MCacheDb mCacheDb = actorRef.underlyingActor();
        Assert.assertEquals(mCacheDb.getValue("key"), "value");
    }


}
