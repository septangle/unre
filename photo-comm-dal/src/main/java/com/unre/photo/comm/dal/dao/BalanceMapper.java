package com.unre.photo.comm.dal.dao;

import com.unre.photo.comm.dal.model.Balance;

public interface BalanceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Balance record);

    int insertSelective(Balance record);

    Balance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Balance record);

    int updateByPrimaryKey(Balance record);
}