package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.PanoramaEngineResponse;

public interface IPanoramaEngineFacade {
	
	/**
	 * 新增benaco scan
	 * 
	 * @param request.MemberDto 
	 *  
	 * @return Response.PanoramaEngineDto.scanId
	 * @throws Exception
	 */
	PanoramaEngineResponse createScan(PanoramaEngineRequest request) throws Exception;

	/**
	 * 添加scan的3D照片
	 * 
	 * @param request.MemberDto 
	 *  
	 * @return Response.PanoramaEngineDto
	 * @throws Exception
	 */
	PanoramaEngineResponse addPhotos(PanoramaEngineRequest request) throws Exception;

	/**
	 * 开始将3D照片制作成全景照片
	 * 
	 * @param request.MemberDto 
	 *  
	 * @return PanoramaEngineResponse
	 * @throws Exception
	 */
	PanoramaEngineResponse startProcessing(PanoramaEngineRequest request) throws Exception;

	/**
	 * 查询 scan
	 * 
	 * @param MemberDto 
	 *  
	 * @return PanoramaEngineResponse.scanId
	 * @throws Exception
	 */
	PanoramaEngineResponse queryScanStatus(PanoramaEngineRequest request) throws Exception;
	
	/**
	 * 执行3D图处理
	 *   
	 * @return PanoramaEngineResponse.scanId
	 * @throws Exception
	 */
	PanoramaEngineResponse startPanoramaProcess() throws Exception;
	
	/**
	 * 添加拼接完成照片
	 * 
	 * @param  PanoramaEngineDto
	 * 
	 * @return PanoramaResponse
	 * @throws Exception
	 */
	PanoramaEngineResponse addPhotoStitchCompleted(PanoramaEngineRequest request) throws Exception;

}
