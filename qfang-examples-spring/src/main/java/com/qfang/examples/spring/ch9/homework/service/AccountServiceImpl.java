package com.qfang.examples.spring.ch9.homework.service;

import com.qfang.examples.spring.ch9.homework.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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

}
