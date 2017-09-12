package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.PanoramaDto;

@SuppressWarnings("serial")
public class PanoramaResponse extends BaseResponse{
	private PanoramaDto panoramaDto;

	private List<PanoramaDto> ProcessSourceDtoList;

	

	public PanoramaDto getPanoramaDto() {
		return panoramaDto;
	}

	public void setPanoramaDto(PanoramaDto panoramaDto) {
		this.panoramaDto = panoramaDto;
	}

	public List<PanoramaDto> getProcessSourceDtoList() {
		return ProcessSourceDtoList;
	}

	public void setProcessSourceDtoList(List<PanoramaDto> ProcessSourceDtoList) {
		this.ProcessSourceDtoList = ProcessSourceDtoList;
	}

}
