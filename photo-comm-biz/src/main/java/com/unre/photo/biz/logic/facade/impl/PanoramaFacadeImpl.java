package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.biz.logic.facade.IPanoramaFacade;
import com.unre.photo.biz.request.PanoramaRequest;
import com.unre.photo.biz.response.PanoramaResponse;
import com.unre.photo.comm.AppConstants;

@Service("PanoramaFacade")
public class PanoramaFacadeImpl implements IPanoramaFacade {

	@Autowired
	private IPanoramaBiz panoramaBiz;

	@Override
	public PanoramaResponse queryProcessSource(PanoramaRequest request) throws Exception {
		List<PanoramaDto> processItemList = panoramaBiz.queryProcessSource(request.getPanoramaDto());
		PanoramaResponse response = new PanoramaResponse();
		response.setProcessSourceDtoList(processItemList);
		return response;
	}

	@Override
	public PanoramaResponse findProcessSourceById(PanoramaRequest request) throws Exception {
		PanoramaResponse response = new PanoramaResponse();
		PanoramaDto processItemParm = request.getPanoramaDto();
		if (processItemParm != null) {
			PanoramaDto ProcessSourceDto = panoramaBiz.findProcessSourceById(processItemParm.getId());
			response.setPanoramaDto(ProcessSourceDto);
		}
		return response;
	}

	@Override
	public PanoramaResponse updatePanorama(PanoramaRequest request) throws Exception {
		PanoramaResponse response = new PanoramaResponse();
		boolean flag = panoramaBiz.updatePanorama(request.getPanoramaDto());
		String code = flag ? AppConstants.SUCCESS_CODE : AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;
	}




}
