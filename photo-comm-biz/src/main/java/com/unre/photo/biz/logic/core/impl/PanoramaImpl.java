package com.unre.photo.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.OrderMapper;
import com.unre.photo.comm.dal.dao.PanoramaMapper;
import com.unre.photo.comm.dal.model.Order;
import com.unre.photo.comm.dal.model.Panorama;
import com.unre.photo.util.ModelUtil;

@Service
public class PanoramaImpl implements IPanoramaBiz {

	@Autowired
	private PanoramaMapper panoramaMapper;
	
	@Autowired 
	private OrderMapper orderMapper;

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
	public List<PanoramaDto> queryProcessSource(PanoramaDto processSourceDto) throws BusinessException {
		List<PanoramaDto> MemberDtoList = new ArrayList<PanoramaDto>();
		try {
			Panorama ProcessSource = ModelUtil.dtoToModel(processSourceDto, Panorama.class);
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
	public PanoramaDto addProcessSource(PanoramaDto processSourceDto) throws BusinessException {
		PanoramaDto photoRes = null;
		Panorama processSource = ModelUtil.dtoToModel(processSourceDto, Panorama.class);
		panoramaMapper.insertSelective(processSource);
		Long id =processSource.getId();
		photoRes = findProcessSourceById(id);
		return photoRes;
	}


	@Override
	public boolean updatePanorama(OrderDto orderDto) throws BusinessException {
		boolean flag = false;
		try {
			orderDto.setIsDeleted("1");
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			int a = orderMapper.updateByPrimaryKeySelective(order);
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
