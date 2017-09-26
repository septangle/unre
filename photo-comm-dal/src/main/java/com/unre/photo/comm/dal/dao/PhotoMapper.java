package com.unre.photo.comm.dal.dao;

import com.unre.photo.comm.dal.model.Photo;

public interface PhotoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Photo record);

    int insertSelective(Photo record);

    Photo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Photo record);

    int updateByPrimaryKey(Photo record);
    
    /****************自定义查询*******************/    

}