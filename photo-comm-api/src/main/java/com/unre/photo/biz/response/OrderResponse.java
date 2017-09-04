package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.OrderDto;

@SuppressWarnings("serial")
public class OrderResponse extends BaseResponse {

	private OrderDto processDto;

	private List<OrderDto> processDtoList;



	public OrderDto getProcessDto() {
		return processDto;
	}

	public void setProcessDto(OrderDto processDto) {
		this.processDto = processDto;
	}

	public List<OrderDto> getProcessDtoList() {
		return processDtoList;
	}

	public void setProcessDtoList(List<OrderDto> processDtoList) {
		this.processDtoList = processDtoList;
	}



}
