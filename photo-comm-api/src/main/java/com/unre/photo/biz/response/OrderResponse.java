package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.OrderDto;

@SuppressWarnings("serial")
public class OrderResponse extends BaseResponse {

	private OrderDto orderDto;

	private List<OrderDto> processDtoList;
	

	public OrderDto getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(OrderDto orderDto) {
		this.orderDto = orderDto;
	}

	public List<OrderDto> getProcessDtoList() {
		return processDtoList;
	}

	public void setProcessDtoList(List<OrderDto> processDtoList) {
		this.processDtoList = processDtoList;
	}



}
