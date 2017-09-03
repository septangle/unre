package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.PanoramaDto;

@SuppressWarnings("serial")
public class ProcessSourceResponse extends BaseResponse{
	private PanoramaDto ProcessSourceDto;

	private List<PanoramaDto> ProcessSourceDtoList;

	public PanoramaDto getProcessSourceDto() {
		return ProcessSourceDto;
	}

	public void setProcessSourceDto(PanoramaDto ProcessSourceDto) {
		this.ProcessSourceDto = ProcessSourceDto;
	}

	public List<PanoramaDto> getProcessSourceDtoList() {
		return ProcessSourceDtoList;
	}

	public void setProcessSourceDtoList(List<PanoramaDto> ProcessSourceDtoList) {
		this.ProcessSourceDtoList = ProcessSourceDtoList;
	}

}
