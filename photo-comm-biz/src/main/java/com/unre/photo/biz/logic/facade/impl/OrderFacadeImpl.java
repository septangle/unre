package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.SceneDto;
import com.unre.photo.biz.logic.core.IOrderBiz;
import com.unre.photo.biz.logic.facade.IOrderFacade;
import com.unre.photo.biz.request.OrderRequest;
import com.unre.photo.biz.response.OrderResponse;
import com.unre.photo.comm.AppConstants;

/**
 * @author TDH
 *
 */
@Service
public class OrderFacadeImpl implements IOrderFacade {

	@Autowired
	private IOrderBiz iorderBiz;

	@Override
	public OrderResponse findProcessById(OrderRequest request) throws Exception {
		OrderResponse response = new OrderResponse();
		OrderDto ProcessParm = request.getOrderDto();
		if (ProcessParm != null) {
			OrderDto orderDto = iorderBiz.findOrderById(ProcessParm.getId());
			response.setOrderDto(orderDto);
		}
		return response;
	}

	@Override
	public OrderResponse updateOrder(OrderRequest request) throws Exception {
		OrderResponse response = new OrderResponse();
		OrderDto orderDto = request.getOrderDto();
		boolean flag = iorderBiz.updateOrder(orderDto);
		String code = flag ? AppConstants.SUCCESS_CODE : AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;
	}

	//查询当前用户场景
	@Override
	public OrderResponse findCurrMemberPanorama(OrderRequest request) throws Exception {
		List<SceneDto> sceneDtoList = iorderBiz.queryMemberScene(request.getOrderDto());
		OrderResponse response = new OrderResponse();
		response.setSceneDtoList(sceneDtoList);;
		return response;
	}

	@Override
	public OrderResponse removeOrderById(OrderRequest request) throws Exception {
		OrderResponse response = new OrderResponse();
		OrderDto orderDto = request.getOrderDto();
		boolean flag = iorderBiz.removeOrder(orderDto);
		String code = flag ? AppConstants.SUCCESS_MESSAGE : AppConstants.FAIL_MESSAGE;
		response.setCode(code);
		return response;
	}

	@Override
	public OrderResponse queryOrder(OrderRequest request) throws Exception {
		OrderResponse response = new OrderResponse();
		OrderDto orderDto = request.getOrderDto();
		List<OrderDto> orderDtoList = iorderBiz.queryOrder(orderDto);
		response.setOrderDtoList(orderDtoList);
		return response;
	}

}
