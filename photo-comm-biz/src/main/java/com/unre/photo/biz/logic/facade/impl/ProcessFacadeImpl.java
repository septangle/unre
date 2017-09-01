package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.ProcessDto;
import com.unre.photo.biz.logic.core.IProcessBiz;
import com.unre.photo.biz.logic.facade.IProcessFacade;
import com.unre.photo.biz.request.ProcessRequest;
import com.unre.photo.biz.response.ProcessResponse;
import com.unre.photo.comm.AppConstants;

/**
 * @author TDH
 *
 */
@Service
public class ProcessFacadeImpl implements IProcessFacade {

	@Autowired
	private IProcessBiz processBiz;

	@Override
	public ProcessResponse queryProcess(ProcessRequest request) throws Exception {
		List<ProcessDto> ProcessDtoList = processBiz.queryProcess(request.getProcessDto());
		ProcessResponse response = new ProcessResponse();
		response.setProcessDtoList(ProcessDtoList);
		return response;
	}

	@Override
	public ProcessResponse findProcessById(ProcessRequest request) throws Exception {
		ProcessResponse response = new ProcessResponse();
		ProcessDto ProcessParm = request.getProcessDto();
		if (ProcessParm != null) {
			ProcessDto ProcessDto = processBiz.findProcessById(ProcessParm.getId());
			response.setProcessDto(ProcessDto);
		}
		return response;
	}

	@Override
	public ProcessResponse deleteProcess(ProcessRequest request) throws Exception {
		ProcessResponse response = new ProcessResponse();
		boolean flag= processBiz.deleteProcess(request.getProcessDto().getId());
		String code = flag? AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;		
	}

	@Override
	public ProcessResponse updateProcess(ProcessRequest request) throws Exception {
		ProcessResponse response = new ProcessResponse();
		ProcessDto ProcessDto = request.getProcessDto();
		boolean flag = processBiz.updateProcess(ProcessDto);
		String code = flag? AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;
	}

}
