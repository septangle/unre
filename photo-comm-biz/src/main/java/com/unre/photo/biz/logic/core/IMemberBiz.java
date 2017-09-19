package com.unre.photo.biz.logic.core;

import java.util.List;

import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.dto.PriceDto;
import com.unre.photo.biz.exception.BusinessException;

/**
 * 登录用户信息查询、校验
 * @author TDH
 */
public interface IMemberBiz {

	/**
	 * 通过ID查询memberId
	 * 
	 * @param memberId  --id
	 * @return MemberDto --Dto
	 * 
	 * @throws BusinessException
	 */
	public MemberDto findMemberById(Long memberId) throws BusinessException;

	/**
	 * 查询满足条件的Member
	 * 
	 * @param MemberDto --Dto
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public List<MemberDto> queryMember(MemberDto memberDto) throws BusinessException;

	/**
	 * 新增Member
	 * 
	 * @param MemberDto  
	 * @return MemberDto
	 * @throws BusinessException
	 */
	public MemberDto addMember(MemberDto memberDto) throws BusinessException;

	/**
	 * 更新Member
	 * 
	 * @param MemberDto --要更新的MemberDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public void updateMember(MemberDto memberDto) throws BusinessException;

	/**
	 * 删除Member
	 * 
	 * @param id --要删除的Member ID
	 * 
	 * @return boolean 
	 * @throws BusinessException
	 */
	public void deleteMember(Long id) throws BusinessException;

	/**
	 * 通过member_name和password登录
	 * 
	 * @param member_name   --member_name
	 * @param password     --password
	 * @return MemberDto -Dto
	 * @throws BusinessException
	 */
	public MemberDto queryLoginUser(MemberDto memberDto) throws BusinessException;

	/**
	 * 查询当前用户单价
	 */
	public PriceDto calculateMerberPrice(MemberDto memberDto) throws BusinessException;
	
	/**
	 * 
	 * 
	 * @param
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public List<MemberDto> queryAllMember() throws BusinessException;
}
