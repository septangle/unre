package com.unre.photo.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.ProcessSourceDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IProcessSourceBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.ProcessSourceMapper;
import com.unre.photo.comm.dal.model.ProcessSource;
import com.unre.photo.util.ModelUtil;

@Service
public class ProcessSourceImpl implements IProcessSourceBiz {

	@Autowired
	private ProcessSourceMapper processSourceMapper;

	private static final Log LOGGER = LogFactory.getLog(ProcessSourceImpl.class);

	@Override
	public ProcessSourceDto findProcessSourceById(Long processSourceId) throws BusinessException {
		ProcessSourceDto ProcessSourceDto = null;

		try {
			ProcessSource ProcessSource = processSourceMapper.selectByPrimaryKey(processSourceId);
			ProcessSourceDto = ModelUtil.modelToDto(ProcessSource, ProcessSourceDto.class);
		} catch (Exception e) {

			throw new BusinessException("err", "err");
		}
		return ProcessSourceDto;
	}

	@Override
	public List<ProcessSourceDto> queryProcessSource(ProcessSourceDto processSourceDto) throws BusinessException {
		List<ProcessSourceDto> MemberDtoList = new ArrayList<ProcessSourceDto>();
		try {
			ProcessSource ProcessSource = ModelUtil.dtoToModel(processSourceDto, ProcessSource.class);
			List<ProcessSource> ProcessSourceList = processSourceMapper.selectBySelective(ProcessSource);
			if (!CollectionUtils.isEmpty(ProcessSourceList)) {
				for (ProcessSource p : ProcessSourceList) {
					MemberDtoList.add(ModelUtil.modelToDto(p, ProcessSourceDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return MemberDtoList;
	}

	@Override
	public ProcessSourceDto addProcessSource(ProcessSourceDto processSourceDto) throws BusinessException {
		ProcessSourceDto photoRes = null;
		ProcessSource processSource = ModelUtil.dtoToModel(processSourceDto, ProcessSource.class);
		processSourceMapper.insertSelective(processSource);
		Long id =processSource.getId();
		photoRes = findProcessSourceById(id);
		return photoRes;
	}

	@Override
	public void deleteProcessSource(Long id) throws BusinessException {

	}

	@Override
	public boolean updateProcessSource(ProcessSourceDto processSourceDto) throws BusinessException {
		boolean flag = false;
		try {
			ProcessSource processSource = ModelUtil.dtoToModel(processSourceDto, ProcessSource.class);
			int a = processSourceMapper.updateBySelective(processSource);
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
