package com.qfang.examples;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author huxianyong
 * @date 2017/8/16
 * @since 1.0
 */
public class PersonPoolFactory implements PooledObjectFactory<Person> {
    @Override
    public PooledObject<Person> makeObject() throws Exception {
        PooledObject<Person> pooledObject=new DefaultPooledObject(new Person("1","zhangsan"));

        return pooledObject;
    }

    @Override
    public void destroyObject(PooledObject<Person> p) throws Exception {
        p.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<Person> p) {
        return true;
    }

    @Override
    public void activateObject(PooledObject<Person> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<Person> p) throws Exception {
        System.out.println("回收对象:"+p.getObject().toString());
    }
}
