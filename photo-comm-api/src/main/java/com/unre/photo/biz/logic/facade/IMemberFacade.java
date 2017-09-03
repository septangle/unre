package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.MemberRequest;
import com.unre.photo.biz.response.MemberResponse;

public interface IMemberFacade {

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public MemberResponse queryMember(MemberRequest request) throws Exception;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public MemberResponse findMemberById(MemberRequest request) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 */
	public void deleteMember(Long id) throws Exception;

	/**
	 * 更新Member
	 * 
	 * @param request
	 * 
	 * @return void
	 * @throws BusinessException
	 */
	public void updateMember(MemberRequest request) throws Exception;

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public MemberResponse login(MemberRequest request) throws Exception;

	/**
	 * 注册
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public MemberResponse register(MemberRequest request) throws Exception;

}