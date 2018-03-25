package com.qfang.examples.spring.cache;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-03
 * @since: 1.0
 */
public class CacheMainTest {

    public static void main(String[] args) {
        // 调用 Person 无参构造函数 person1 == person2
        Person proxyPerson = DynamicProxyFactory.getProxy(Person.class);
        Person person1 = (Person) proxyPerson.get(null);
        System.out.println(person1);

        Person proxyPerson2 = DynamicProxyFactory.getProxy(Person.class);
        Person person2 = (Person) proxyPerson2.get(null);
        System.out.println(person2);

        // person3 & person4 调用 Person(String name) 构造函数，因为参数值一样，类型一样 person3 = person4
        Person proxyPerson3 = DynamicProxyFactory.getProxy(Person.class);
        Person person3 = (Person) proxyPerson3.get(new String[] {"lisi"});
        System.out.println(person3);

        Person proxyPerson4 = DynamicProxyFactory.getProxy(Person.class);
        Person person4 = (Person) proxyPerson4.get(new String[] {"lisi"});
        System.out.println(person4);

        // person3 != person5 虽然构造函数一样，构造函数参数类型一样，但参数值不同，获取的是不同对象
        Person proxyPerson5 = DynamicProxyFactory.getProxy(Person.class);
        Person person5 = (Person) proxyPerson5.get(new String[] {"zhangshan"});
        System.out.println(person5);

        // user1 == uer2 构造函数一样
        User proxyUser1 = DynamicProxyFactory.getProxy(User.class);
        User user1 = (User) proxyUser1.get(null);
        System.out.println(user1);

        User proxyUser2 = DynamicProxyFactory.getProxy(User.class);
        User user2 = (User) proxyUser2.get(null);
        System.out.println(user2);
    }

}
