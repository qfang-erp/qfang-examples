package com.qfang.examples.springbatch.mapper;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author huxianyong
 * @date 2018/7/31
 * @since 1.0
 */
public class ReportItemPreparedStatementSetter implements ItemPreparedStatementSetter {
    @Override
    public void setValues(Object o, PreparedStatement preparedStatement) throws SQLException {

    }
}
