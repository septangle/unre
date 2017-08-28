package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.PhotoScanItemRequest;
import com.unre.photo.biz.response.PhotoScanItemResponse;

public interface IPhotoScanItemFacade {
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PhotoScanItemResponse queryPhotoScanItem(PhotoScanItemRequest request) throws Exception;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PhotoScanItemResponse findPhotoScanItemById(PhotoScanItemRequest request) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 */
	public PhotoScanItemResponse deletePhotoScanItem(Long id) throws Exception;
	
	/**
	 * 更新PhotoScanItem
	 * 
	 * @param request
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public PhotoScanItemResponse updatePhotoScanItem(PhotoScanItemRequest request) throws Exception;
}
