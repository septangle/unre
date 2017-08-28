package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.PhotoScanRequest;
import com.unre.photo.biz.response.PhotoScanResponse;

public interface IPhotoScanFacade {

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PhotoScanResponse queryPhotoScan(PhotoScanRequest request) throws Exception;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PhotoScanResponse findPhotoScanById(PhotoScanRequest request) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 */
	public PhotoScanResponse deletePhotoScan(Long id) throws Exception;

	/**
	 * 更新PhotoScan
	 * 
	 * @param request
	 * 
	 * @return PhotoScanResponse
	 * @throws BusinessException
	 */
	public PhotoScanResponse updatePhotoScan(PhotoScanRequest request) throws Exception;

}
