package com.unre.photo.biz.request;

import com.unre.photo.biz.dto.BalanceTraceDto;

@SuppressWarnings("serial")
public class BalanceTraceRequest extends BaseRequest{
	
	private BalanceTraceDto balanceTraceDto;

	public BalanceTraceDto getBalanceTraceDto() {
		return balanceTraceDto;
	}

	public void setBalanceTraceDto(BalanceTraceDto balanceTraceDto) {
		this.balanceTraceDto = balanceTraceDto;
	}
	
	

}
