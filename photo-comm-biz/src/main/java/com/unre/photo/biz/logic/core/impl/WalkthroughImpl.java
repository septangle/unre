package com.unre.photo.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.PublicScanDto;
import com.unre.photo.biz.dto.WalkthroughDto;
import com.unre.photo.biz.logic.core.IWalkthroughBiz;
import com.unre.photo.comm.dal.dao.WalkthroughMapper;
import com.unre.photo.comm.dal.model.PublicScan;
import com.unre.photo.comm.dal.model.Walkthrough;
import com.unre.photo.util.ModelUtil;

@Service
public class WalkthroughImpl implements IWalkthroughBiz{
	
	@Autowired
	private WalkthroughMapper walkthroughMapper;

	@SuppressWarnings("unused")
	private static final Log LOGGER = LogFactory.getLog(WalkthroughImpl.class);

	@Override
	public boolean addWalkthrough(WalkthroughDto walkthroughDto) {
		boolean flag=false;
		try {
			Walkthrough walkthrough = ModelUtil.dtoToModel(walkthroughDto, Walkthrough.class);
			int i=walkthroughMapper.insertSelective(walkthrough);
			if (i!=0) {
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<PublicScanDto> findPublicScanList() {
		List<PublicScanDto> publicScanDtoList = new ArrayList<PublicScanDto>();
		List<PublicScan> publicScanList=walkthroughMapper.getPubilcScan();
		if (!CollectionUtils.isEmpty(publicScanList)) {
			for (PublicScan p : publicScanList) {
				publicScanDtoList.add(ModelUtil.modelToDto(p, PublicScanDto.class));
			}
		}
		return publicScanDtoList;
	}

}
