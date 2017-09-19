package com.qfang.examples;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author huxianyong
 * @date 2017/8/16
 * @since 1.0
 */
public class PersonPool  {

    private static GenericObjectPool<Person> genericObjectPool;

    static {
        GenericObjectPoolConfig config=new GenericObjectPoolConfig();
        config.setMaxTotal(200);
        config.setMaxWaitMillis(2000);
        config.setMaxIdle(10);
        config.setTestOnBorrow(true);
        genericObjectPool=new GenericObjectPool<Person>(new PersonPoolFactory(),config);
    }

    public Person borrowObject()  {
        Person person = null;
        try {
            person =genericObjectPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;
    }


    public void returnObject(Person person){
        genericObjectPool.returnObject(person);
    }

    public void close(){
        genericObjectPool.close();
    }




}
