package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.ProcessDto;

@SuppressWarnings("serial")
public class ProcessResponse extends BaseResponse {

	private ProcessDto ProcessDto;

	private List<ProcessDto> ProcessDtoList;

	public ProcessDto getProcessDto() {
		return ProcessDto;
	}

	public void setProcessDto(ProcessDto ProcessDto) {
		this.ProcessDto = ProcessDto;
	}

	public List<ProcessDto> getProcessDtoList() {
		return ProcessDtoList;
	}

	public void setProcessDtoList(List<ProcessDto> ProcessDtoList) {
		this.ProcessDtoList = ProcessDtoList;
	}

}
