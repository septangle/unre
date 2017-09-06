package com.unre.photo.comm.dal.dao;

import com.unre.photo.comm.dal.model.MemberLevelItem;

public interface MemberLevelItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberLevelItem record);

    int insertSelective(MemberLevelItem record);

    MemberLevelItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberLevelItem record);

    int updateByPrimaryKey(MemberLevelItem record);
    
    //根据value取得MemberLevelItem信息
    MemberLevelItem selectByValue(String value);
}