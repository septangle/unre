package com.unre.photo.comm.dal.dao;

import java.math.BigDecimal;

import com.unre.photo.comm.dal.model.MemberLevelItem;

public interface MemberLevelItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberLevelItem record);

    int insertSelective(MemberLevelItem record);

    MemberLevelItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberLevelItem record);

    int updateByPrimaryKey(MemberLevelItem record);
    
    
    /****************自定义查询*******************/
    
    // 根据会员等级值取得会员等级定义信息
    MemberLevelItem selectByValue(String value);
    
    // 根据一年的充值金额取得应该设定的会员等级
    MemberLevelItem selectByAmount(BigDecimal amount);
}