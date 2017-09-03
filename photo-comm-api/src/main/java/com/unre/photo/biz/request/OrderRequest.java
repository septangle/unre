package com.unre.photo.biz.request;

import com.unre.photo.biz.dto.OrderDto;

@SuppressWarnings("serial")
public class OrderRequest extends BaseRequest {

	private OrderDto ProcessDto;

	public OrderDto getProcessDto() {
		return ProcessDto;
	}

	public void setProcessDto(OrderDto ProcessDto) {
		this.ProcessDto = ProcessDto;
	}

}
