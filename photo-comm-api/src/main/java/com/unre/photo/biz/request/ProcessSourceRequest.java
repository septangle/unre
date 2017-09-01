package com.unre.photo.biz.request;

import com.unre.photo.biz.dto.ProcessSourceDto;

@SuppressWarnings("serial")
public class ProcessSourceRequest extends BaseRequest{
	private ProcessSourceDto ProcessSourceDto;

	public ProcessSourceDto getProcessSourceDto() {
		return ProcessSourceDto;
	}

	public void setProcessSourceDto(ProcessSourceDto ProcessSourceDto) {
		this.ProcessSourceDto = ProcessSourceDto;
	}


	
	
	

}
