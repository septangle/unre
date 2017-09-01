package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.logic.core.IMemberBiz;
import com.unre.photo.biz.logic.facade.IMemberFacade;
import com.unre.photo.biz.request.MemberRequest;
import com.unre.photo.biz.response.MemberResponse;
/**
 * @author TDH
 *
 */
@Service
public class MemberFacadeImpl implements IMemberFacade {

	@Autowired
	private IMemberBiz memberBiz;

	@Override
	public MemberResponse queryMember(MemberRequest request) throws Exception {
		List<MemberDto> memberList = memberBiz.queryMember(request.getMemberDto());
		MemberResponse response = new MemberResponse();
		response.setMemberDtoList(memberList);
		return response;
	}

	@Override
	public MemberResponse findMemberById(MemberRequest request) throws Exception {
		MemberResponse response = new MemberResponse();
		MemberDto memberParm = request.getMemberDto();
		if (memberParm != null) {
			MemberDto MemberDto = memberBiz.findMemberById(memberParm.getId());
			response.setMemberDto(MemberDto);
		}
		return response;
	}

	@Override
	public void deleteMember(Long id) throws Exception {

	}

	@Override
	public void updateMember(MemberRequest request) throws Exception {

	}

	// 登录
	@Override
	public MemberResponse login(MemberRequest request) throws Exception {
        MemberResponse response = new MemberResponse();
		response = new MemberResponse();
		MemberDto MemberDto = memberBiz.queryLoginUsers(request.getMemberDto());
		response.setMemberDto(MemberDto);
		return response;

	}

	//注册
	@Override
	public MemberResponse register(MemberRequest request) throws Exception {
		MemberResponse response = new MemberResponse();
			response = new MemberResponse();
			MemberDto MemberDto = memberBiz.addMember(request.getMemberDto());
			response.setMemberDto(MemberDto);
           return response;
	}

}
