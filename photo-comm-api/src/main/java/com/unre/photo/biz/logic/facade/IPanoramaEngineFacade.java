package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.PanoramaEngineResponse;

public interface IPanoramaEngineFacade {
	
	/**
	 * 新增benaco scan
	 * 
	 * @param request.photoMemberDto 
	 *  
	 * @return Response.PanoramaEngineDto.scanId
	 * @throws Exception
	 */
	PanoramaEngineResponse createScan(PanoramaEngineRequest request) throws Exception;

	/**
	 * 添加scan的3D照片
	 * 
	 * @param request.photoMemberDto 
	 *  
	 * @return Response.PanoramaEngineDto
	 * @throws Exception
	 */
	PanoramaEngineResponse addPhotos(PanoramaEngineRequest request) throws Exception;

	/**
	 * 开始将3D照片制作成全景照片
	 * 
	 * @param request.photoMemberDto 
	 *  
	 * @return PanoramaEngineResponse
	 * @throws Exception
	 */
	PanoramaEngineResponse startProcessing(PanoramaEngineRequest request) throws Exception;

	/**
	 * 新增benaco scan
	 * 
	 * @param request.photoMemberDto 
	 *  
	 * @return PanoramaEngineResponse.scanId
	 * @throws Exception
	 */
	//PanoramaEngineResponse generateScan(PanoramaEngineRequest request) throws Exception;

	/**
	 * 查询 scan
	 * 
	 * @param photoMemberDto 
	 *  
	 * @return PanoramaEngineResponse.scanId
	 * @throws Exception
	 */
	PanoramaEngineResponse queryScanStatus(PanoramaEngineRequest request) throws Exception;

}
