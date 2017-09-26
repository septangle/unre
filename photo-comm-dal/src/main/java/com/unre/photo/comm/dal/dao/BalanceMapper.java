package com.unre.photo.comm.dal.dao;

import com.unre.photo.comm.dal.model.Balance;

public interface BalanceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Balance record);

    int insertSelective(Balance record);

    Balance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Balance record);

    int updateByPrimaryKey(Balance record);
    
    
    /****************自定义查询*******************/
    
    // 根据会员ID取得会员余额信息
    Balance selectByMemberID(Long id);
}