package com.unre.photo.biz.logic.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.PanoramaEngineDto;
import com.unre.photo.biz.logic.core.IPanoramaEngineBiz;
import com.unre.photo.biz.logic.facade.IPanoramaEngineFacade;
import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.PanoramaEngineResponse;
import com.unre.photo.comm.AppConstants;

@Service
public class PanoramaEngineFacadeImpl implements IPanoramaEngineFacade {

	@Autowired
	private IPanoramaEngineBiz panoramaEngineBiz;

	@Override
	public PanoramaEngineResponse createScan(PanoramaEngineRequest request) throws Exception {
		PanoramaEngineResponse retResponse = new PanoramaEngineResponse();
		PanoramaEngineDto pEngineDto = panoramaEngineBiz.createScan(request.getPanoramaEngineDto());
		retResponse.setPanoramaEngineDto(pEngineDto);
		return retResponse;
	}

	@Override
	public PanoramaEngineResponse addPhotos(PanoramaEngineRequest request) throws Exception {
		PanoramaEngineResponse retResponse = new PanoramaEngineResponse();
		
		// 1.创建scan id
		PanoramaEngineDto panoramaEngineDto = panoramaEngineBiz.createScan(request.getPanoramaEngineDto());
		String benacoScanId = panoramaEngineDto.getBenacoScanId();
		// 2.上传照片
		request.getPanoramaEngineDto().setBenacoScanId(benacoScanId);
		boolean flg  = panoramaEngineBiz.addPhotos(request.getPanoramaEngineDto());
		String code = flg? AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;
		retResponse.setCode(code);
		panoramaEngineDto.setBenacoScanId(benacoScanId);
		retResponse.setPanoramaEngineDto(panoramaEngineDto);
		return retResponse;
	}

	@Override
	public PanoramaEngineResponse startProcessing(PanoramaEngineRequest request) throws Exception {
		PanoramaEngineResponse retResponse = new PanoramaEngineResponse();
		boolean flg = panoramaEngineBiz.startProcessing(request.getPanoramaEngineDto());
		String code = flg? AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;
		retResponse.setCode(code);
		return retResponse;
	}

	/*@Override
	public PanoramaEngineResponse generateScan(PanoramaEngineRequest request) throws Exception {
		PanoramaEngineResponse response = new PanoramaEngineResponse();

		// 1.创建scan id
		PanoramaEngineDto panoramaEngineDto = panoramaEngineBiz.createScan(request.getPanoramaEngineDto());
		String scanId = panoramaEngineDto.getBenacoScanId();
		// 2.上传照片
		panoramaEngineBiz.addPhotos(panoramaEngineDto);
		// 3.开始处理
		boolean flg = panoramaEngineBiz.startProcessing(panoramaEngineDto);
		String code = flg? AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;
	}*/

	@Override
	public PanoramaEngineResponse queryScanStatus(PanoramaEngineRequest request) throws Exception {
		PanoramaEngineResponse retResponse = new PanoramaEngineResponse();
		PanoramaEngineDto panoramaEngineDto = panoramaEngineBiz.queryScanStatus(request.getPanoramaEngineDto());
		retResponse.setPanoramaEngineDto(panoramaEngineDto);
		return retResponse;
	}

}
