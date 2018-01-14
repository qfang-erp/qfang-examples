package com.qfang.examples.spring.ch9.homework.service;

import com.qfang.examples.spring.ch9.homework.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;


/**
 * Created by walle on 2017/5/7.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void transferAmount(String fromUsername, String toUsername, int amount) {
        accountDao.subtractAmount(fromUsername, amount);
//         int i = 5 / 0;   // 模拟抛出异常，测试事务回滚
        accountDao.addAmount(toUsername, amount);

        System.out.println("transfer amount success");
    }

    public Consumer<String> getConsumer() {
        return str -> {
            if(str.equals("str"))
                System.out.print(str);
        };
    }

    public Function<String, Boolean> getFunction() {
        // 参数是 String，返回值 Boolean
        return str -> str.equals("str");
    }

    public static void main(String[] args) {
        int big = 10000;
        long sum = 0;
        for(int i = 0; i < big; i++) {
            sum += i;
        }
        System.out.println(sum);

        List<Integer> listI = new ArrayList<>();
        IntStream.range(0, big).forEach(listI::add);
        System.out.println(listI.size());

        int s = listI.stream().parallel()
                .peek(i -> System.out.println(Thread.currentThread().getName()))
                .reduce(0, Integer::sum);
        System.out.println(s);
    }

}
