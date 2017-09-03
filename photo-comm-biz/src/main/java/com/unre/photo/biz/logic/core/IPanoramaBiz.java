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
	 * 删除ProcessItem
	 * 
	 * @param id --要删除的ProcessItem( ID
	 * 
	 * @return boolean 
	 * @throws BusinessException
	 */
	public void deleteProcessSource(Long id) throws BusinessException;
	
	/**
	 * 更新ProcessItem
	 * 
	 * @param ProcessItem --要更新的ProcessItem
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updateProcessSource(PanoramaDto processSourceDto) throws BusinessException;
	
}