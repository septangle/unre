package com.unre.photo.biz.logic.core;

import com.unre.photo.biz.dto.PanoramaEngineDto;

public interface IPanoramaEngineBiz {

	/**
	 * 新增benaco scan
	 * 
	 * @param panoramaEngineDto 
	 *  
	 * @return PanoramaEngineDto.scanId
	 * @throws Exception
	 */
	public PanoramaEngineDto createScan(PanoramaEngineDto panoramaEngineDto) throws Exception;

	/**
	 * 添加scan的3D照片
	 * 
	 * @param MemberDto 
	 *  
	 * @return PanoramaEngineDto
	 * @throws Exception
	 */
	public PanoramaEngineDto addPhotos(PanoramaEngineDto panoramaEngineDto) throws Exception;

	/**
	 * 调用Benaco Process API开始将3D照片制作成全景照片
	 * 
	 * @param MemberDto 
	 *  
	 * @return boolean
	 * @throws Exception
	 */
	public boolean startBenacoProcess(PanoramaEngineDto panoramaEngineDto) throws Exception;
	
	/**
	 * 开始将2D照片制作成全景照片
	 * 
	 * @param pEngineDto 
	 *  
	 * @return boolean
	 * @throws Exception
	 */
	public boolean startPhotoProcess(PanoramaEngineDto pEngineDto) throws Exception;
	
	/**
	 * 开始将3D照片制作成场景照片
	 * 
	 * @param pEngineDto 
	 *  
	 * @return boolean
	 * @throws Exception
	 */
	public boolean startPanoramaProcess(PanoramaEngineDto pEngineDto) throws Exception;
	
	/**
	 * 查询Scan状态
	 * 
	 * @param MemberDto 
	 *  
	 * @return PanoramaEngineDto
	 * @throws Exception
	 */
	public PanoramaEngineDto queryScanStatus(PanoramaEngineDto panoramaEngineDto) throws Exception;

	/**
	 * 预览Scan
	 * 
	 * @param MemberDto 
	 *  
	 * @return PanoramaEngineDto
	 * @throws Exception
	 */
	public PanoramaEngineDto previewScan(PanoramaEngineDto panoramaEngineDto) throws Exception;

}
