package com.unre.photo.biz.logic.core;

import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.exception.BusinessException;

/**
 * 
 * @author 
 */
public interface IMemberLevelBiz {
	
	/**
	 * 
	 * 
	 * @param MemberDto
	 * 
	 * @return 
	 * @throws BusinessException
	 */
	public void insertMemberLevel(MemberDto memberDto) throws BusinessException;
}