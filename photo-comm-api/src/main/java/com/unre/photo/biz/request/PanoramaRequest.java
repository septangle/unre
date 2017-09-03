package com.unre.photo.biz.request;

import com.unre.photo.biz.dto.PanoramaDto;

@SuppressWarnings("serial")
public class PanoramaRequest extends BaseRequest{
	private PanoramaDto panoramaDto;

	public PanoramaDto getProcessSourceDto() {
		return panoramaDto;
	}

	public void setProcessSourceDto(PanoramaDto panoramaDto) {
		this.panoramaDto = panoramaDto;
	}
	
}
