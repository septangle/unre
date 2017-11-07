package com.unre.photo.comm.dal.dao;

import java.util.List;

import com.unre.photo.comm.dal.model.Panorama;

public interface PanoramaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Panorama record);

    int insertSelective(Panorama record);

    Panorama selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Panorama record);
    
    int updateBySelective(Panorama record);
  
    /****************自定义查询*******************/    
   
    
    List<Panorama> selectBySelective(Panorama record);
    
    List<Panorama> selectByPhotoCount(Panorama record);
    
    List<Panorama> selectPendingProcessPanorama(Panorama record);
    
    int updatePanoramabyOrderId(Panorama record);
    

}