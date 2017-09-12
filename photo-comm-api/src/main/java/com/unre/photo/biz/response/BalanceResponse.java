package com.unre.photo.biz.response;

import com.unre.photo.biz.dto.BalanceDto;

@SuppressWarnings("serial")
public class BalanceResponse extends BaseResponse {

	private BalanceDto balanceDto;

	public BalanceDto getBalanceDto() {
		return balanceDto;
	}

	public void setBalanceDto(BalanceDto balanceDto) {
		this.balanceDto = balanceDto;
	}
}
