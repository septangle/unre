package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.ProcessSourceDto;

@SuppressWarnings("serial")
public class ProcessSourceResponse extends BaseResponse{
	private ProcessSourceDto ProcessSourceDto;

	private List<ProcessSourceDto> ProcessSourceDtoList;

	public ProcessSourceDto getProcessSourceDto() {
		return ProcessSourceDto;
	}

	public void setProcessSourceDto(ProcessSourceDto ProcessSourceDto) {
		this.ProcessSourceDto = ProcessSourceDto;
	}

	public List<ProcessSourceDto> getProcessSourceDtoList() {
		return ProcessSourceDtoList;
	}

	public void setProcessSourceDtoList(List<ProcessSourceDto> ProcessSourceDtoList) {
		this.ProcessSourceDtoList = ProcessSourceDtoList;
	}

}
