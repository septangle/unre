package com.unre.photo.biz.request;

import com.unre.photo.biz.dto.BalanceDto;

@SuppressWarnings("serial")
public class BalanceRequest extends BaseRequest {

	private BalanceDto balanceDto;

	public BalanceDto getBalanceDto() {
		return balanceDto;
	}

	public void setBalanceDto(BalanceDto balanceDto) {
		this.balanceDto = balanceDto;
	}
}
