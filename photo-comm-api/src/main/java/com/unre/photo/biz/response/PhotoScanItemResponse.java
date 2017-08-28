package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.PhotoScanItemDto;

@SuppressWarnings("serial")
public class PhotoScanItemResponse extends BaseResponse{
	private PhotoScanItemDto photoScanItemDto;

	private List<PhotoScanItemDto> photoScanItemDtoList;

	public PhotoScanItemDto getPhotoScanItemDto() {
		return photoScanItemDto;
	}

	public void setPhotoScanItemDto(PhotoScanItemDto photoScanItemDto) {
		this.photoScanItemDto = photoScanItemDto;
	}

	public List<PhotoScanItemDto> getPhotoScanItemDtoList() {
		return photoScanItemDtoList;
	}

	public void setPhotoScanItemDtoList(List<PhotoScanItemDto> photoScanItemDtoList) {
		this.photoScanItemDtoList = photoScanItemDtoList;
	}

}
