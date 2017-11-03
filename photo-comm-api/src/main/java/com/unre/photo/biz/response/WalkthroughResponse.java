package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.PublicScanDto;

@SuppressWarnings("serial")
public class WalkthroughResponse extends BaseResponse{

	private List<PublicScanDto> publicScanList;

	public List<PublicScanDto> getPublicScanList() {
		return publicScanList;
	}

	public void setPublicScanList(List<PublicScanDto> publicScanList) {
		this.publicScanList = publicScanList;
	}
	
}
