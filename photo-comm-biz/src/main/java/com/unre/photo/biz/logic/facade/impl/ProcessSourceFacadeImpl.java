package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.biz.logic.facade.IProcessSourceFacade;
import com.unre.photo.biz.request.PanoramaRequest;
import com.unre.photo.biz.response.ProcessSourceResponse;
import com.unre.photo.comm.AppConstants;

@Service("ProcessSourceFacade")
public class ProcessSourceFacadeImpl implements IProcessSourceFacade {

	private IPanoramaBiz processSourceBiz;

	@Override
	public ProcessSourceResponse queryProcessSource(PanoramaRequest request) throws Exception {
		List<PanoramaDto> processItemList = processSourceBiz.queryProcessSource(request.getProcessSourceDto());
		ProcessSourceResponse response = new ProcessSourceResponse();
		response.setProcessSourceDtoList(processItemList);
		;
		return response;
	}

	@Override
	public ProcessSourceResponse findProcessSourceById(PanoramaRequest request) throws Exception {
		ProcessSourceResponse response = new ProcessSourceResponse();
		PanoramaDto processItemParm = request.getProcessSourceDto();
		if (processItemParm != null) {
			PanoramaDto ProcessSourceDto = processSourceBiz.findProcessSourceById(processItemParm.getId());
			response.setProcessSourceDto(ProcessSourceDto);
		}
		return response;
	}

	@Override
	public ProcessSourceResponse deleteProcessSource(Long id) throws Exception {
		return null;

	}

	@Override
	public ProcessSourceResponse updateProcessSource(PanoramaRequest request) throws Exception {
		ProcessSourceResponse response = new ProcessSourceResponse();
		boolean flag = processSourceBiz.updateProcessSource(request.getProcessSourceDto());
		String code = flag ? AppConstants.SUCCESS_CODE : AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;

	}

}
