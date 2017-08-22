package com.unre.photo.biz.logic.core.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unre.photo.biz.dto.PhotoScanDto;
import com.unre.photo.biz.dto.PhotoScanItemDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IPhotoScanBiz;
import com.unre.photo.biz.logic.core.IPhotoScanItemBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.PhotoScanMapper;
import com.unre.photo.comm.dal.model.PhotoScan;
import com.unre.photo.util.ModelUtil;

@Service
public class PhotoScanImpl implements IPhotoScanBiz {

	@Autowired
	private PhotoScanMapper photoScanMapper;
	
	@Autowired
	private IPhotoScanItemBiz photoScanItemBizImpl;

	private static final Log LOGGER = LogFactory.getLog(PhotoScanImpl.class);

	@Override
	public PhotoScanDto findPhotoScanById(Long photoScanId) throws BusinessException {
		PhotoScanDto photoScanDto = null;

		try {
			PhotoScan photoScan = photoScanMapper.selectByPrimaryKey(photoScanId);
			photoScanDto = ModelUtil.modelToDto(photoScan, PhotoScanDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_FIND_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_FIND_ERROR_CODE,
					AppConstants.SCAN_FIND_ERROR_MESSAGE);
		}
		return photoScanDto;
	}

	@Override
	public List<PhotoScanDto> queryPhotoScan(PhotoScanDto photoScanDto) throws BusinessException {
		List<PhotoScanDto> photoScanoList = new ArrayList<PhotoScanDto>();
		try {
			PhotoScan photoScan = ModelUtil.dtoToModel(photoScanDto,
					PhotoScan.class);
			List<PhotoScan> photoScanList = photoScanMapper.selectBySelective(photoScan);
			if (!CollectionUtils.isEmpty(photoScanList)) {
				for (PhotoScan p : photoScanList) {
					photoScanoList.add(ModelUtil.modelToDto(p,
							PhotoScanDto.class));
				}
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_QUERY_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_QUERY_ERROR_CODE,
					AppConstants.SCAN_QUERY_ERROR_MESSAGE);
		}
		return photoScanoList;
	}

	@Override
	public PhotoScanDto addPhotoScan(PhotoScanDto photoScanDto) throws BusinessException {
		PhotoScanDto retPhotoDto = null;
		try {
			PhotoScan photoScan = ModelUtil.dtoToModel(photoScanDto, PhotoScan.class);
			photoScanMapper.insertSelective(photoScan);
			Long id = photoScan.getId();
			retPhotoDto = this.findPhotoScanById(id);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_ADD_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_ADD_ERROR_CODE,
					AppConstants.SCAN_ADD_ERROR_MESSAGE);
		}
		return retPhotoDto;
	}

	@Override
	public void updatePhotoScan(PhotoScanDto photoScanDto) throws BusinessException {
		try {
			PhotoScan photoScan = ModelUtil.dtoToModel(photoScanDto, PhotoScan.class);
			int flag = photoScanMapper.updateBySelective(photoScan);
			if (1 != flag) { // flag == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
						AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_UPDATE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
					AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
		}
	}

	@Override
	public void updatePhotoScanByBenacoId(PhotoScanDto photoScanDto) throws BusinessException {
		try {
			PhotoScan photoScan = ModelUtil.dtoToModel(photoScanDto, PhotoScan.class);
			int flag = photoScanMapper.updatePhotoScanByBenacoId(photoScan);
			if (1 != flag) { // flag == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
						AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_UPDATE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
					AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
		}
	}

	@Override
	public void deletePhotoScan(Long id) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public boolean saveUploadedImages(String benacoScanId, List<File> imageFiles) throws BusinessException {
		boolean flg = false;
		try {
			//1.更新scan状态
			PhotoScan pScan = new PhotoScan();
			pScan.setBenacoScanId(benacoScanId);
			pScan.setStatus(AppConstants.SFILE_UPLOAD_COMPLETE);
			int i = photoScanMapper.updatePhotoScanByBenacoId(pScan);
			
			//2. 新增scan item
			for (File f : imageFiles) {
				String imageFullPath = f.getPath() + f.getName();
				PhotoScanItemDto pScanItemDto = new PhotoScanItemDto();
				pScanItemDto.setBenacoScanId(benacoScanId);
				pScanItemDto.setImagePath(imageFullPath);
				photoScanItemBizImpl.addPhotoScanItem(pScanItemDto);
			}
			flg = true;
			
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_SAVE_IMAGE_ERROR_CODE,
					AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE);
		}
		
		return flg;
	}
    
}
