package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.BalanceTraceDto;

@SuppressWarnings("serial")
public class BalanceTraceResponse extends BaseResponse {

	private BalanceTraceDto balanceTraceDto;
	
	private List<BalanceTraceDto> balanceTraceDtoList;

	public BalanceTraceDto getBalanceTraceDto() {
		return balanceTraceDto;
	}

	public void setBalanceTraceDto(BalanceTraceDto balanceTraceDto) {
		this.balanceTraceDto = balanceTraceDto;
	}

	public List<BalanceTraceDto> getBalanceTraceDtoList() {
		return balanceTraceDtoList;
	}

	public void setBalanceTraceDtoList(List<BalanceTraceDto> balanceTraceDtoList) {
		this.balanceTraceDtoList = balanceTraceDtoList;
	}
	
	
}
