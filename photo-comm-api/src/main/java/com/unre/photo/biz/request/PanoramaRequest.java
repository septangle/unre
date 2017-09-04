package com.unre.photo.biz.request;

import com.unre.photo.biz.dto.PanoramaDto;

@SuppressWarnings("serial")
public class PanoramaRequest extends BaseRequest{
	private PanoramaDto panoramaDto;

	public PanoramaDto getPanoramaDto() {
		return panoramaDto;
	}

	public void setPanoramaDto(PanoramaDto panoramaDto) {
		this.panoramaDto = panoramaDto;
	}

	
	
}
