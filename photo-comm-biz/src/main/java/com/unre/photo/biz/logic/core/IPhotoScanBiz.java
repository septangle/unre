package com.unre.photo.biz.logic.core;

import java.io.File;
import java.util.List;

import com.unre.photo.biz.dto.PhotoScanDto;
import com.unre.photo.biz.exception.BusinessException;

/**
 * 图片扫描处理记录
 * @author TDH
 */
public interface IPhotoScanBiz {

	/**
	 * 通过ID查询PhotoScan
	 * 
	 * @param photoScanId  --id
	 * @return PhotoScanDto --Dto
	 * 
	 * @throws BusinessException
	 */
	public PhotoScanDto findPhotoScanById(Long photoScanId) throws BusinessException;

	/**
	 * 查询满足条件的PhotoScan
	 * 
	 * @param PhotoScanDto --Dto
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public List<PhotoScanDto> queryPhotoScan(PhotoScanDto photoScanDto) throws BusinessException;

	/**
	 * 新增PhotoScan
	 * 
	 * @param PhotoScanDto  
	 * @return PhotoScanDto
	 * @throws BusinessException
	 */
	public PhotoScanDto addPhotoScan(PhotoScanDto photoScanDto) throws BusinessException;

	/**
	 * 更新PhotoScan
	 * 
	 * @param PhotoScanDto --要更新的PhotoScanDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updatePhotoScan(PhotoScanDto photoScanDto) throws BusinessException;

	/**
	 * 更新PhotoScan
	 * 
	 * @param PhotoScanDto --要更新的PhotoScanDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updatePhotoScanByBenacoId(PhotoScanDto photoScanDto) throws BusinessException;
	
	/**
	 * 删除PhotoScan
	 * 
	 * @param id --要删除的PhotoScan ID
	 * 
	 * @return boolean 
	 * @throws BusinessException
	 */
	public boolean deletePhotoScan(Long id) throws BusinessException;
	
	/**
	 * 保存已经成功上传至benaco的图片
	 * 
	 * @param benacoScanId
	 * @param imageFiles --要保存的文件
	 * 
	 * @return boolean 
	 * @throws BusinessException
	 */
	public boolean saveUploadedImages(String benacoScanId,List<File> imageFiles) throws BusinessException;
	

	/**
	 * 更新status信息
	 * 
	 */
	public void queryStatus();

}
