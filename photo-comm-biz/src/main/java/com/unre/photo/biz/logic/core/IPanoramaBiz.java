package com.unre.photo.biz.logic.core;

import java.util.List;

import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.exception.BusinessException;


public interface IPanoramaBiz {

	/**
	 * 通过ID查询ProcessItem
	 * 
	 * @param ProcessItemId  --id
	 * @return ProcessSourceDto --Dto
	 * 
	 * @throws BusinessException
	 */
	public PanoramaDto findProcessSourceById(Long processSourceId) throws BusinessException;
	
	/**
	 * 查询满足条件的ProcessItem
	 * 
	 * @param MemberDto --Dto
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public List<PanoramaDto> queryProcessSource(PanoramaDto processSourceDto) throws BusinessException;


	/**
	 * 新增ProcessItem
	 * 
	 * @param MemberDto  
	 * @return MemberDto
	 * @throws BusinessException
	 */
	public PanoramaDto addProcessSource(PanoramaDto processSourceDto) throws BusinessException;

	/**
	 * 更新ProcessItem
	 * 
	 * @param panoramaDto --要更新的panoramaDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updatePanorama(PanoramaDto panoramaDto) throws BusinessException;
	
	/**
	 * 根据order_id查询
	 * @return list
	 */
	public List<PanoramaDto> selectByPhoto(PanoramaDto panoramaDto) throws BusinessException;
    
	
	/**
	 * 查询未拼接好2D照片的全景图的ProcessItem
	 * 
	 * @param processSourceDto.orderId --订单ID
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public List<PanoramaDto> queryUnStitchProcessSource(PanoramaDto processSourceDto) throws BusinessException;
	
	/**
	 * 查询拼接好2D照片的全景图的ProcessItem
	 * 
	 * @param processSourceDto.orderId --订单ID
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public List<PanoramaDto> queryStitchedProcessSource(PanoramaDto processSourceDto) throws BusinessException;
    
	/**
	 * benaco 处理完成之后更新 panorama 状态
	 * 
	 * @param orderId --订单ID
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public boolean updateAfterBenacoProcess(Long orderId) throws BusinessException;
	/**
	 * 更新ProcessItem
	 * 
	 * @param panoramaDto --要更新的panoramaDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updatePanoramaByPrimaryKey(PanoramaDto panoramaDto) throws BusinessException;
	
}
