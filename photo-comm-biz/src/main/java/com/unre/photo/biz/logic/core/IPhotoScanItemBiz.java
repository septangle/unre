package com.unre.photo.biz.logic.core;

import java.util.List;
import com.unre.photo.biz.dto.PhotoScanItemDto;
import com.unre.photo.biz.exception.BusinessException;


public interface IPhotoScanItemBiz {

	/**
	 * 通过ID查询PhotoScanItem
	 * 
	 * @param photoScanItemId  --id
	 * @return PhotoScanItemDto --Dto
	 * 
	 * @throws BusinessException
	 */
	public PhotoScanItemDto findPhotoScanItemById(Long photoScanItemId) throws BusinessException;
	
	/**
	 * 查询满足条件的PhotoScanItem
	 * 
	 * @param photoMemberDto --Dto
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public List<PhotoScanItemDto> queryPhotoScanItem(PhotoScanItemDto photoScanItemDto) throws BusinessException;


	/**
	 * 新增PhotoScanItem
	 * 
	 * @param photoMemberDto  
	 * @return PhotoMemberDto
	 * @throws BusinessException
	 */
	public PhotoScanItemDto addPhotoScanItem(PhotoScanItemDto photoScanItemDto) throws BusinessException;



	/**
	 * 删除PhotoScanItem
	 * 
	 * @param id --要删除的PhotoScanItem( ID
	 * 
	 * @return boolean 
	 * @throws BusinessException
	 */
	public void deletePhotoScanItem(Long id) throws BusinessException;
	
	/**
	 * 更新PhotoScanItem
	 * 
	 * @param PhotoScanItem --要更新的PhotoScanItem
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updatePhotoScanItem(PhotoScanItemDto photoScanItemDto) throws BusinessException;
	
}
