package com.unre.photo.comm.dal.dao;

import java.util.List;

import com.unre.photo.comm.dal.model.PhotoScan;

public interface PhotoScanMapper {
	int deleteByPrimaryKey(Long id);

	int insert(PhotoScan record);

	int insertSelective(PhotoScan record);

	PhotoScan selectByPrimaryKey(Long id);
	

	int updateByPrimaryKeySelective(PhotoScan record);

	int updateByPrimaryKey(PhotoScan record);
	
	// --------------------------------
	List<PhotoScan> selectBySelective(PhotoScan record);
	
	int updateBySelective(PhotoScan record);
	
	int updatePhotoScanByBenacoId(PhotoScan record);
	
	List<PhotoScan> selByStatus();
	
	int upByStatus(PhotoScan record);

	

}