package com.unre.photo.comm.dal.dao;

import java.util.List;

import com.unre.photo.comm.dal.model.Panorama;

public interface PanoramaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Panorama record);

    int insertSelective(Panorama record);

    Panorama selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Panorama record);

    int updateByPrimaryKey(Panorama record);
    
    int updateBySelective(Panorama record);
    
    List<Panorama> selectBySelective(Panorama record);
}