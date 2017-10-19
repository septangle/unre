package com.unre.photo.biz.request;


import com.unre.photo.biz.dto.OrderDto;

@SuppressWarnings("serial")
public class OrderRequest extends BaseRequest {

	private OrderDto orderDto;
	

	public OrderDto getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(OrderDto orderDto) {
		this.orderDto = orderDto;
	}



	

}
