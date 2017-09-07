package com.unre.photo.comm.dal.dao;

import com.unre.photo.comm.dal.model.BalanceTrace;

public interface BalanceTraceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BalanceTrace record);

    int insertSelective(BalanceTrace record);

    BalanceTrace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BalanceTrace record);

    int updateByPrimaryKey(BalanceTrace record);
}