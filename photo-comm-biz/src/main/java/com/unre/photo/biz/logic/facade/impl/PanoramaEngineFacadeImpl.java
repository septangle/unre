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
		// 2.上传图片到server
	    PanoramaEngineDto EngineDto = panoramaEngineBiz.addPhotos(request.getPanoramaEngineDto());
		Long orderId = EngineDto.getOrderId();
		retResponse.setCode(AppConstants.SUCCESS_CODE);
		panoramaEngineDto.setBenacoScanId(benacoScanId);
		panoramaEngineDto.setOrderId(orderId);
		retResponse.setPanoramaEngineDto(panoramaEngineDto);
		return retResponse;
	}

	@Override
	public PanoramaEngineResponse startProcessing(PanoramaEngineRequest request) throws Exception {
		PanoramaEngineResponse retResponse = new PanoramaEngineResponse();
		boolean flg = panoramaEngineBiz.startBenacoProcess(request.getPanoramaEngineDto());
		String code = flg ? AppConstants.SUCCESS_CODE : AppConstants.FAIL_CODE;
		retResponse.setCode(code);
		return retResponse;
	}
	
	@Override
	public PanoramaEngineResponse startPanoramaProcess() throws Exception {
		PanoramaEngineResponse retResponse = new PanoramaEngineResponse();

    	PanoramaEngineDto peDto = new PanoramaEngineDto();
    	String apiBaseUrl = AppConstants.BENACO_HOST + AppConstants.BENACO_BASEPATH + AppConstants.BENACO_SCAN + "/";
    	peDto.setApiBaseUrl(apiBaseUrl);
		peDto.setApiKey(AppConstants.BENACO_PRIVATE_KEY);
		
		boolean flg = panoramaEngineBiz.startPanoramaProcess(peDto);
		String code = flg ? AppConstants.SUCCESS_CODE : AppConstants.FAIL_CODE;
		retResponse.setCode(code);
		return retResponse;
	}

	@Override
	public PanoramaEngineResponse queryScanStatus(PanoramaEngineRequest request) throws Exception {
		PanoramaEngineResponse retResponse = new PanoramaEngineResponse();
		PanoramaEngineDto panoramaEngineDto = panoramaEngineBiz.queryScanStatus(request.getPanoramaEngineDto());
		retResponse.setPanoramaEngineDto(panoramaEngineDto);
		return retResponse;
	}

}
