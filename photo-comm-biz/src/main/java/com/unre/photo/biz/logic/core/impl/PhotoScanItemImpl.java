package com.unre.photo.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.PhotoScanItemDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IPhotoScanItemBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.PhotoScanItemMapper;
import com.unre.photo.comm.dal.model.PhotoScanItem;
import com.unre.photo.util.ModelUtil;

@Service
public class PhotoScanItemImpl implements IPhotoScanItemBiz {

	@Autowired
	private PhotoScanItemMapper photoScanItemMapper;

	private static final Log LOGGER = LogFactory.getLog(PhotoScanItemImpl.class);

	@Override
	public PhotoScanItemDto findPhotoScanItemById(Long photoScanItemId) throws BusinessException {
		PhotoScanItemDto photoScanItemDto = null;

		try {
			PhotoScanItem photoScanItem = photoScanItemMapper.selectByPrimaryKey(photoScanItemId);
			photoScanItemDto = ModelUtil.modelToDto(photoScanItem, PhotoScanItemDto.class);
		} catch (Exception e) {

			throw new BusinessException("err", "err");
		}
		return photoScanItemDto;
	}

	@Override
	public List<PhotoScanItemDto> queryPhotoScanItem(PhotoScanItemDto photoScanItemDto) throws BusinessException {
		List<PhotoScanItemDto> photoMemberDtoList = new ArrayList<PhotoScanItemDto>();
		try {
			PhotoScanItem photoScanItem = ModelUtil.dtoToModel(photoScanItemDto, PhotoScanItem.class);
			List<PhotoScanItem> photoScanItemList = photoScanItemMapper.selectBySelective(photoScanItem);
			if (!CollectionUtils.isEmpty(photoScanItemList)) {
				for (PhotoScanItem p : photoScanItemList) {
					photoMemberDtoList.add(ModelUtil.modelToDto(p, PhotoScanItemDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return photoMemberDtoList;
	}

	@Override
	public PhotoScanItemDto addPhotoScanItem(PhotoScanItemDto photoScanItemDto) throws BusinessException {
		PhotoScanItemDto photoRes = null;
		PhotoScanItem PhotoScanItem = ModelUtil.dtoToModel(photoScanItemDto, PhotoScanItem.class);
		photoScanItemMapper.insertSelective(PhotoScanItem);
		Long id = PhotoScanItem.getId();
		photoRes = findPhotoScanItemById(id);
		return photoRes;
	}

	@Override
	public void deletePhotoScanItem(Long id) throws BusinessException {

	}

	@Override
	public boolean updatePhotoScanItem(PhotoScanItemDto photoScanItemDto) throws BusinessException {
		boolean flag = false;
		try {
			PhotoScanItem PhotoScanItem = ModelUtil.dtoToModel(photoScanItemDto, PhotoScanItem.class);
			int a = photoScanItemMapper.updateBySelective(PhotoScanItem);
			if (1 != a) { // flag == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCANITEM_UPDATE_ERROR_CODE,
						AppConstants.SCANITEM_UPDATE_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCANITEM_UPDATE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCANITEM_UPDATE_ERROR_CODE,
					AppConstants.SCANITEM_UPDATE_ERROR_MESSAGE);
		}
		return flag;
	}


}
