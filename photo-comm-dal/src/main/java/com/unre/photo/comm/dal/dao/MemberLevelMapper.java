package com.unre.photo.comm.dal.dao;

import com.unre.photo.comm.dal.model.MemberLevel;

public interface MemberLevelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberLevel record);

    int insertSelective(MemberLevel record);

    MemberLevel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberLevel record);

    int updateByPrimaryKey(MemberLevel record);
    
    /****************自定义查询*******************/
    
    
}