package com.unre.photo.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.PanoramaMapper;
import com.unre.photo.comm.dal.model.Panorama;
import com.unre.photo.util.ModelUtil;

@Service
public class PanoramaImpl implements IPanoramaBiz {

	@Autowired
	private PanoramaMapper panoramaMapper;

	private static final Log LOGGER = LogFactory.getLog(PanoramaImpl.class);

	@Override
	public PanoramaDto findProcessSourceById(Long processSourceId) throws BusinessException {
		PanoramaDto ProcessSourceDto = null;

		try {
			Panorama ProcessSource = panoramaMapper.selectByPrimaryKey(processSourceId);
			ProcessSourceDto = ModelUtil.modelToDto(ProcessSource, PanoramaDto.class);
		} catch (Exception e) {

			throw new BusinessException("err", "err");
		}
		return ProcessSourceDto;
	}

	@Override
	public List<PanoramaDto> queryProcessSource(PanoramaDto panoramaDto) throws BusinessException {
		List<PanoramaDto> MemberDtoList = new ArrayList<PanoramaDto>();
		try {
			Panorama panorama = ModelUtil.dtoToModel(panoramaDto, Panorama.class);
			List<Panorama> ProcessSourceList = panoramaMapper.selectBySelective(panorama);
			if (!CollectionUtils.isEmpty(ProcessSourceList)) {
				for (Panorama p : ProcessSourceList) {
					MemberDtoList.add(ModelUtil.modelToDto(p, PanoramaDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return MemberDtoList;
	}

	@Override
	public PanoramaDto addProcessSource(PanoramaDto processSourceDto) throws BusinessException {
		PanoramaDto photoRes = null;
		Panorama processSource = ModelUtil.dtoToModel(processSourceDto, Panorama.class);
		panoramaMapper.insertSelective(processSource);
		Long id =processSource.getId();
		photoRes = findProcessSourceById(id);
		return photoRes;
	}


	@Override
	public boolean updatePanorama(PanoramaDto panoramaDto) throws BusinessException {
		boolean flag = false;
		try {
			Panorama panorama = ModelUtil.dtoToModel(panoramaDto, Panorama.class);
			int i = panoramaMapper.updateBySelective(panorama);
			if (i != 1) { // flag == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCANITEM_UPDATE_ERROR_CODE,
						AppConstants.SCANITEM_UPDATE_ERROR_MESSAGE);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(AppConstants.SCANITEM_UPDATE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCANITEM_UPDATE_ERROR_CODE,
					AppConstants.SCANITEM_UPDATE_ERROR_MESSAGE);
		}
		return flag;
	}
	

	@Override
	public List<PanoramaDto> selectByPhoto(PanoramaDto panoramaDto) throws BusinessException {
		List<PanoramaDto> MemberDtoList = new ArrayList<PanoramaDto>();
		try {
			Panorama ProcessSource = ModelUtil.dtoToModel(panoramaDto, Panorama.class);
			List<Panorama> ProcessSourceList = panoramaMapper.selectBySelective(ProcessSource);
			if (!CollectionUtils.isEmpty(ProcessSourceList)) {
				for (Panorama p : ProcessSourceList) {
					MemberDtoList.add(ModelUtil.modelToDto(p, PanoramaDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return MemberDtoList;
	}
    
	@Override
	public List<PanoramaDto> queryUnStitchProcessSource(PanoramaDto processSourceDto) throws BusinessException {
		List<PanoramaDto> panoramaDtoList = new ArrayList<PanoramaDto>();
		try {
			Panorama ProcessSource = ModelUtil.dtoToModel(processSourceDto, Panorama.class);
			ProcessSource.setStitchStatus(AppConstants.PANORAMA_UNSTITCH);
			List<Panorama> ProcessSourceList = panoramaMapper.selectPendingProcessPanorama(ProcessSource);
			if (!CollectionUtils.isEmpty(ProcessSourceList)) {
				for (Panorama p : ProcessSourceList) {
					panoramaDtoList.add(ModelUtil.modelToDto(p, PanoramaDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return panoramaDtoList;
	}

	@Override
	public List<PanoramaDto> queryStitchedProcessSource(PanoramaDto processSourceDto) throws BusinessException {
		List<PanoramaDto> panoramaDtoList = new ArrayList<PanoramaDto>();
		try {
			Panorama ProcessSource = ModelUtil.dtoToModel(processSourceDto, Panorama.class);
			List<Panorama> ProcessSourceList = panoramaMapper.selectPendingProcessPanorama(ProcessSource);
			if (!CollectionUtils.isEmpty(ProcessSourceList)) {
				for (Panorama p : ProcessSourceList) {
					panoramaDtoList.add(ModelUtil.modelToDto(p, PanoramaDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return panoramaDtoList;
	}
	
	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public boolean updateAfterBenacoProcess(Long orderId) throws BusinessException {
		boolean retFlg = false;
		try {
			PanoramaDto panoramaDto = new PanoramaDto();
			panoramaDto.setOrderId(orderId);
			List<PanoramaDto> panDtoList = this.queryProcessSource(panoramaDto);
			if(panDtoList == null || panDtoList.size()==0) {
				return false;
			}
			
			for(PanoramaDto pDto : panDtoList) {
				pDto.setStitchStatus(AppConstants.PANORAMA_STITCHED);
				this.updatePanorama(pDto);
			}
			
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_FIND_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_FIND_ERROR_CODE, AppConstants.SCAN_FIND_ERROR_MESSAGE);
		}
		return retFlg;
	}

	@Override
	public boolean updatePanoramaByPrimaryKey(PanoramaDto panoramaDto) throws BusinessException {
		boolean flag=false;
		Panorama panorama=ModelUtil.dtoToModel(panoramaDto, Panorama.class);
		int i=panoramaMapper.updateBySelective(panorama);
		if (i != 1) { // flag == 1 操作成功,否则操作失败
			throw new BusinessException(AppConstants.UPDATE_PANORAMA_BY_ORDERID_CODE,
					AppConstants.UPDATE_PANORAMA_BY_ORDERID_MESSAGE);
		}
		flag=true;
		return flag;
	}

}
