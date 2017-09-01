package com.unre.photo.comm.dal.dao;

import java.util.List;

import com.unre.photo.comm.dal.model.Process;

public interface ProcessMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Process record);

	int insertSelective(Process record);

	Process selectByPrimaryKey(Long id);
	

	int updateByPrimaryKeySelective(Process record);

	int updateByPrimaryKey(Process record);
	
	// --------------------------------
	List<Process> selectBySelective(Process record);
	
	int updateBySelective(Process record);
	
	int updateProcessByBenacoId(Process record);
	
	List<Process> selByStatus();
	
	int upByStatus(Process record);

	

}