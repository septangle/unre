package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.OrderDto;

@SuppressWarnings("serial")
public class ProcessResponse extends BaseResponse {

	private OrderDto ProcessDto;

	private List<OrderDto> ProcessDtoList;

	public OrderDto getProcessDto() {
		return ProcessDto;
	}

	public void setProcessDto(OrderDto ProcessDto) {
		this.ProcessDto = ProcessDto;
	}

	public List<OrderDto> getProcessDtoList() {
		return ProcessDtoList;
	}

	public void setProcessDtoList(List<OrderDto> ProcessDtoList) {
		this.ProcessDtoList = ProcessDtoList;
	}

}
