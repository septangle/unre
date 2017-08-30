package com.unre.photo.biz.logic.core.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.unre.photo.util.HttpClientResponse;
import com.unre.photo.util.HttpClientUtil;
import com.unre.photo.util.ModelUtil;

import net.sf.json.JSONObject;

@Service("photoScan")
public class PhotoScanImpl implements IPhotoScanBiz {

	@Autowired
	private PhotoScanMapper photoScanMapper;

	@Autowired
	private IPhotoScanItemBiz photoScanItemBiz;

	private static final Log LOGGER = LogFactory.getLog(PhotoScanImpl.class);

	@Override
	public PhotoScanDto findPhotoScanById(Long photoScanId) throws BusinessException {
		PhotoScanDto photoScanDto = null;

		try {
			PhotoScan photoScan = photoScanMapper.selectByPrimaryKey(photoScanId);
			photoScanDto = ModelUtil.modelToDto(photoScan, PhotoScanDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_FIND_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_FIND_ERROR_CODE, AppConstants.SCAN_FIND_ERROR_MESSAGE);
		}
		return photoScanDto;
	}

	@Override
	public List<PhotoScanDto> queryPhotoScan(PhotoScanDto photoScanDto) throws BusinessException {
		List<PhotoScanDto> photoScanoList = new ArrayList<PhotoScanDto>();
		try {
			PhotoScan photoScan = ModelUtil.dtoToModel(photoScanDto, PhotoScan.class);
			List<PhotoScan> photoScanList = photoScanMapper.selectBySelective(photoScan);
			if (!CollectionUtils.isEmpty(photoScanList)) {
				for (PhotoScan p : photoScanList) {
					photoScanoList.add(ModelUtil.modelToDto(p, PhotoScanDto.class));
				}
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_QUERY_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_QUERY_ERROR_CODE, AppConstants.SCAN_QUERY_ERROR_MESSAGE);
		}
		return photoScanoList;
	}

	@SuppressWarnings("unused")
	@Override
	public PhotoScanDto addPhotoScan(PhotoScanDto photoScanDto) throws BusinessException {
		PhotoScanDto retPhotoDto = null;
		try {
			PhotoScan photoScan = ModelUtil.dtoToModel(photoScanDto, PhotoScan.class);
			int i = photoScanMapper.insertSelective(photoScan);
			Long id = photoScan.getId();
			retPhotoDto = this.findPhotoScanById(id);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_ADD_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_ADD_ERROR_CODE, AppConstants.SCAN_ADD_ERROR_MESSAGE);
		}
		return retPhotoDto;
	}

	@Override
	public boolean updatePhotoScan(PhotoScanDto photoScanDto) throws BusinessException {
		boolean flg = false;
		try {
			PhotoScan photoScan = ModelUtil.dtoToModel(photoScanDto, PhotoScan.class);
			int number = photoScanMapper.updateBySelective(photoScan);
			if (number == 0) { // flag == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
						AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
			}
			flg = true;
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_UPDATE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE, AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
		}
		return flg;
	}

	@Override
	public boolean updatePhotoScanByBenacoId(PhotoScanDto photoScanDto) throws BusinessException {
		boolean flg = false;
		try {
			//1.先查出来
			PhotoScan pScanParm = new PhotoScan();
			pScanParm.setBenacoScanId(photoScanDto.getBenacoScanId());
			List<PhotoScan> pScanList = photoScanMapper.selectBySelective(pScanParm);
			if (pScanList.size() == 0 || pScanList.size() > 1) {
				throw new BusinessException(AppConstants.SCAN_BENACO_SCAN_ID_ERROR_CODE,
						AppConstants.SCAN_BENACO_SCAN_ID_ERROR_MESSAGE);
			}
			PhotoScan pScan = pScanList.get(0);
			pScan.setStatus(AppConstants.SFILE_PROCESSING);
			//2.后更新scan状态
			int i = photoScanMapper.updatePhotoScanByBenacoId(pScan);
			if (i != 1) { // i == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
						AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
			}
			flg = true;
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_UPDATE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE, AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
		}

		return flg;
	}

	@Override
	public boolean deletePhotoScan(Long id) throws BusinessException {
		boolean flg = false;
		try {
			int delScan = photoScanMapper.deleteByPrimaryKey(id);
			if (delScan == 1) {
				flg = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(AppConstants.SCAN__DELETE_SCAN_ID_ERROR_CODE,
					AppConstants.SCAN__DELETE_SCAN_ID_ERROR_MESSAGE);
		}
		return flg;

	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public boolean saveUploadedImages(String benacoScanId, List<File> imageFiles) throws BusinessException {
		boolean flg = false;
		try {
			//1.更新scan状态
			PhotoScan pScanParm = new PhotoScan();
			pScanParm.setBenacoScanId(benacoScanId);
			List<PhotoScan> pScanList = photoScanMapper.selectBySelective(pScanParm);
			if (pScanList.size() == 0 || pScanList.size() > 1) {
				throw new BusinessException(AppConstants.SCAN_BENACO_SCAN_ID_ERROR_CODE,
						AppConstants.SCAN_BENACO_SCAN_ID_ERROR_MESSAGE);
			}
			PhotoScan pScan = pScanList.get(0);
			pScan.setStatus(AppConstants.SFILE_UPLOAD_COMPLETE);
			int i = photoScanMapper.updatePhotoScanByBenacoId(pScan);

			//2. 新增scan item
			for (File f : imageFiles) {
				String imageFullPath = f.getPath() + f.getName();
				PhotoScanItemDto pScanItemDto = new PhotoScanItemDto();
				pScanItemDto.setBenacoScanId(benacoScanId);
				pScanItemDto.setImagePath(imageFullPath);
				photoScanItemBiz.addPhotoScanItem(pScanItemDto);
			}
			flg = true;

		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_SAVE_IMAGE_ERROR_CODE,
					AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE);
		}

		return flg;
	}

	public void queryStatus() {
		PhotoScan p = new PhotoScan();
		List<PhotoScan> photoScanList = photoScanMapper.selByStatus();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", "3c7c6941-2204-4ee7-a4b5-0981e0e6e09c");
		JSONObject json = JSONObject.fromObject(params);
		for (int i = 0; i < photoScanList.size(); i++) {
			String photoUrl = "https://beta.benaco.com/api/beta/scans/id/" + photoScanList.get(i).getBenacoScanId() + "/status";
			HttpClientResponse hcResponse = HttpClientUtil.doPost(photoUrl, json);
			String httpRetCode = hcResponse.getCode();
			if ("200".equals(httpRetCode)) {
				String context = hcResponse.getContext();
				JSONObject result = JSONObject.fromObject(context);
				String status = result.getString("status");
				if ("failed".equals(status)) {
					p.setBenacoScanId(photoScanList.get(i).getBenacoScanId());
                    p.setStatus("4");
					photoScanMapper.upByStatus(p);
				}else if ("completed".equals(status)) {
					p.setBenacoScanId(photoScanList.get(i).getBenacoScanId());
					p.setStatus("5");
					photoScanMapper.upByStatus(p);
				}
			}
		}

	}
}
