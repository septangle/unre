package com.unre.photo.biz.logic.core;

import java.util.List;

import com.unre.photo.biz.dto.PublicScanDto;
import com.unre.photo.biz.dto.WalkthroughDto;

/**
 * 场景展示
 * @author zx
 * 
 */
public interface IWalkthroughBiz {

	/**
	 * 新增场景
	 * @param WalkthroughDto
	 * @return boolean
	 */
	public boolean addWalkthrough(WalkthroughDto walkthroughDto);
	
	/**
	 * 查询首页数据
	 * @return list<WalkthroughDto>
	 */
	public List<PublicScanDto> findPublicScanList();
}
