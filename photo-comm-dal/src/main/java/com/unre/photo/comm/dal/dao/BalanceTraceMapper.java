package com.unre.photo.comm.dal.dao;

import java.math.BigDecimal;

import com.unre.photo.comm.dal.model.BalanceTrace;

public interface BalanceTraceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BalanceTrace record);

    int insertSelective(BalanceTrace record);

    BalanceTrace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BalanceTrace record);

    int updateByPrimaryKey(BalanceTrace record);
    
    // 取得会员最新一条流水记录
    BalanceTrace selectLastestRecordByMemberID(Long id);
    
    // 取得会员当年充值总额
    BigDecimal selectAmountSumByMemberID(Long id);
}