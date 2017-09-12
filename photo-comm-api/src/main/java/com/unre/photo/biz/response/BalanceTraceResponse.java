package com.unre.photo.biz.response;

import com.unre.photo.biz.dto.BalanceTraceDto;

@SuppressWarnings("serial")
public class BalanceTraceResponse extends BaseResponse {

	private BalanceTraceDto balanceTraceDto;

	public BalanceTraceDto getBalanceTraceDto() {
		return balanceTraceDto;
	}

	public void setBalanceTraceDto(BalanceTraceDto balanceTraceDto) {
		this.balanceTraceDto = balanceTraceDto;
	}
}
