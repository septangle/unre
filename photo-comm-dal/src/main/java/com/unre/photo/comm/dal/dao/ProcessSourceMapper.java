package com.unre.photo.comm.dal.dao;

import java.util.List;

import com.unre.photo.comm.dal.model.ProcessSource;

public interface ProcessSourceMapper {
	int deleteByPrimaryKey(Long id);

	int insert(ProcessSource record);

	int insertSelective(ProcessSource record);

	ProcessSource selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProcessSource record);

	int updateByPrimaryKey(ProcessSource record);
	
	// --------------------------------
	List<ProcessSource> selectBySelective(ProcessSource record);
	
	int updateBySelective(ProcessSource record);
}