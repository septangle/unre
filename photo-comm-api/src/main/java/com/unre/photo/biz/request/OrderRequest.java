package com.unre.photo.biz.request;


import com.unre.photo.biz.dto.CompleteOrderDto;
import com.unre.photo.biz.dto.OrderDto;

@SuppressWarnings("serial")
public class OrderRequest extends BaseRequest {

	private OrderDto orderDto;
	
	private CompleteOrderDto completeOrderDto;
	
	
	

	
	public CompleteOrderDto getCompleteOrderDto() {
		return completeOrderDto;
	}

	public void setCompleteOrderDto(CompleteOrderDto completeOrderDto) {
		this.completeOrderDto = completeOrderDto;
	}

	public OrderDto getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(OrderDto orderDto) {
		this.orderDto = orderDto;
	}


	

}
