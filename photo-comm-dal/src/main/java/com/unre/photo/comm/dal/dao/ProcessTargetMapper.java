package com.unre.photo.comm.dal.dao;

import com.unre.photo.comm.dal.model.ProcessTarget;

public interface ProcessTargetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProcessTarget record);

    int insertSelective(ProcessTarget record);

    ProcessTarget selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProcessTarget record);

    int updateByPrimaryKey(ProcessTarget record);
}