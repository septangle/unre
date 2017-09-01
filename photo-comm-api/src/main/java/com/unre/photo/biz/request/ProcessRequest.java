package com.unre.photo.biz.request;

import com.unre.photo.biz.dto.ProcessDto;

@SuppressWarnings("serial")
public class ProcessRequest extends BaseRequest {

	private ProcessDto ProcessDto;

	public ProcessDto getProcessDto() {
		return ProcessDto;
	}

	public void setProcessDto(ProcessDto ProcessDto) {
		this.ProcessDto = ProcessDto;
	}

}
