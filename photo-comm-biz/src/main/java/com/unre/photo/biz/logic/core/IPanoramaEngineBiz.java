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
	 * 开始将3D照片制作成全景照片
	 * 
	 * @param MemberDto 
	 *  
	 * @return boolean
	 * @throws Exception
	 */
	public boolean startProcessing(PanoramaEngineDto panoramaEngineDto) throws Exception;
	
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
