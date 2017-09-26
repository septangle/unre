package com.unre.photo.comm.dal.dao;

import com.unre.photo.comm.dal.model.Walkthrough;

public interface WalkthroughMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Walkthrough record);

    int insertSelective(Walkthrough record);

    Walkthrough selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Walkthrough record);

    int updateByPrimaryKey(Walkthrough record);
    
    /****************自定义查询*******************/    

}