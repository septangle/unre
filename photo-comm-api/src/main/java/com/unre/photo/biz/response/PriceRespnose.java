package com.unre.photo.biz.response;

import com.unre.photo.biz.dto.PriceDto;

@SuppressWarnings("serial")
public class PriceRespnose extends BaseResponse{
	
	private PriceDto priceDto;

	public PriceDto getPriceDto() {
		return priceDto;
	}

	public void setPriceDto(PriceDto priceDto) {
		this.priceDto = priceDto;
	}
	
	

}
