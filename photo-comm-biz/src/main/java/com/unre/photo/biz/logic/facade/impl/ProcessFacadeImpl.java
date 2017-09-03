package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.logic.core.IOrderBiz;
import com.unre.photo.biz.logic.facade.IProcessFacade;
import com.unre.photo.biz.request.OrderRequest;
import com.unre.photo.biz.response.ProcessResponse;
import com.unre.photo.comm.AppConstants;

/**
 * @author TDH
 *
 */
@Service
public class ProcessFacadeImpl implements IProcessFacade {

	@Autowired
	private IOrderBiz processBiz;

	@Override
	public ProcessResponse queryProcess(OrderRequest request) throws Exception {
		List<OrderDto> ProcessDtoList = processBiz.queryOrder(request.getProcessDto());
		ProcessResponse response = new ProcessResponse();
		response.setProcessDtoList(ProcessDtoList);
		return response;
	}

	@Override
	public ProcessResponse findProcessById(OrderRequest request) throws Exception {
		ProcessResponse response = new ProcessResponse();
		OrderDto ProcessParm = request.getProcessDto();
		if (ProcessParm != null) {
			OrderDto ProcessDto = processBiz.findOrderById(ProcessParm.getId());
			response.setProcessDto(ProcessDto);
		}
		return response;
	}

	@Override
	public ProcessResponse deleteProcess(OrderRequest request) throws Exception {
		ProcessResponse response = new ProcessResponse();
		boolean flag= processBiz.deleteProcess(request.getProcessDto().getId());
		String code = flag? AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;		
	}

	@Override
	public ProcessResponse updateProcess(OrderRequest request) throws Exception {
		ProcessResponse response = new ProcessResponse();
		OrderDto ProcessDto = request.getProcessDto();
		boolean flag = processBiz.updateOrder(ProcessDto);
		String code = flag? AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;
	}

}
