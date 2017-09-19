package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.CompleteOrderDto;
import com.unre.photo.biz.dto.OrderDto;

@SuppressWarnings("serial")
public class OrderResponse extends BaseResponse {

	private OrderDto orderDto;

	private List<OrderDto> orderDtoList;
	
	private List<CompleteOrderDto> completeOrderDto;
	

	public OrderDto getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(OrderDto orderDto) {
		this.orderDto = orderDto;
	}

	public List<OrderDto> getOrderDtoList() {
		return orderDtoList;
	}

	public void setOrderDtoList(List<OrderDto> orderDtoList) {
		this.orderDtoList = orderDtoList;
	}

	public List<CompleteOrderDto> getCompleteOrderDto() {
		return completeOrderDto;
	}

	public void setCompleteOrderDto(List<CompleteOrderDto> completeOrderDto) {
		this.completeOrderDto = completeOrderDto;
	}


	



}
