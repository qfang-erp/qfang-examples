package com.qfang.examples.spring.ch9.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by walle on 2017/5/7.
 */
@Repository
public class AccountDaoImpl extends JdbcTemplate implements AccountDao {

    @Autowired
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public void addAmount(String username, int amount) {
        this.update("update account a set a.amount = (a.amount + ?) where a.username = ?", amount, username);
    }

    @Override
    public void subtractAmount(String username, int amount) {
        this.update("update account a set a.amount = (a.amount - ?) where a.username = ?", amount, username);
    }

}
