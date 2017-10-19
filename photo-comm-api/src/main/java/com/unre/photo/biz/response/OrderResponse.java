package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.SceneDto;

@SuppressWarnings("serial")
public class OrderResponse extends BaseResponse {

	private OrderDto orderDto;

	private List<OrderDto> orderDtoList;
	
	private List<SceneDto> sceneDtoList;
		

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

	public List<SceneDto> getSceneDtoList() {
		return sceneDtoList;
	}

	public void setSceneDtoList(List<SceneDto> sceneDtoList) {
		this.sceneDtoList = sceneDtoList;
	}



	



}
