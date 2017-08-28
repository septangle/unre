package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.unre.photo.biz.dto.PhotoScanItemDto;
import com.unre.photo.biz.logic.core.IPhotoScanItemBiz;
import com.unre.photo.biz.logic.facade.IPhotoScanItemFacade;
import com.unre.photo.biz.request.PhotoScanItemRequest;
import com.unre.photo.biz.response.PhotoScanItemResponse;
import com.unre.photo.comm.AppConstants;

@Service("photoScanItemFacade")
public class PhotoScanItemFacadeImpl implements IPhotoScanItemFacade {

	private IPhotoScanItemBiz photoScanItemBiz;

	@Override
	public PhotoScanItemResponse queryPhotoScanItem(PhotoScanItemRequest request) throws Exception {
		List<PhotoScanItemDto> PhotoScanItemList = photoScanItemBiz.queryPhotoScanItem(request.getPhotoScanItemDto());
		PhotoScanItemResponse response = new PhotoScanItemResponse();
		response.setPhotoScanItemDtoList(PhotoScanItemList);
		;
		return response;
	}

	@Override
	public PhotoScanItemResponse findPhotoScanItemById(PhotoScanItemRequest request) throws Exception {
		PhotoScanItemResponse response = new PhotoScanItemResponse();
		PhotoScanItemDto photoScanItemParm = request.getPhotoScanItemDto();
		if (photoScanItemParm != null) {
			PhotoScanItemDto photoScanItemDto = photoScanItemBiz.findPhotoScanItemById(photoScanItemParm.getId());
			response.setPhotoScanItemDto(photoScanItemDto);
		}
		return response;
	}

	@Override
	public PhotoScanItemResponse deletePhotoScanItem(Long id) throws Exception {
		return null;

	}

	@Override
	public PhotoScanItemResponse updatePhotoScanItem(PhotoScanItemRequest request) throws Exception {
		PhotoScanItemResponse response = new PhotoScanItemResponse();
		boolean flag = photoScanItemBiz.updatePhotoScanItem(request.getPhotoScanItemDto());
		String code = flag ? AppConstants.SUCCESS_CODE : AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;

	}

}
