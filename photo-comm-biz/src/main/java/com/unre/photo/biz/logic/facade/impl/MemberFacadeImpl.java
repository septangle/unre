package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.dto.MemberInformationDto;
import com.unre.photo.biz.dto.PriceDto;
import com.unre.photo.biz.logic.core.IMemberBiz;
import com.unre.photo.biz.logic.facade.IMemberFacade;
import com.unre.photo.biz.request.MemberRequest;
import com.unre.photo.biz.response.MemberResponse;
import com.unre.photo.biz.response.PriceRespnose;
import com.unre.photo.comm.AppConstants;

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
	public MemberResponse updatePassword(MemberRequest request) throws Exception {
		MemberResponse response = new MemberResponse();
		boolean flag = memberBiz.updatePassword(request.getMemberDto());
		String code = flag ? AppConstants.SUCCESS_CODE : AppConstants.FAIL_CODE;
		response.setCode(code);
		return response;
	}

	// 登录
	@Override
	public MemberResponse login(MemberRequest request) throws Exception {
		MemberResponse response = new MemberResponse();
		response = new MemberResponse();
		MemberDto memberDto = memberBiz.queryLoginUser(request.getMemberDto());
		response.setMemberDto(memberDto);
		return response;

	}

	//注册
	@Override
	public MemberResponse register(MemberRequest request) throws Exception {
		MemberResponse response = new MemberResponse();
		response = new MemberResponse();
		MemberDto memberDto = memberBiz.addMember(request.getMemberDto());
		response.setMemberDto(memberDto);
		return response;
	}

	@Override
	public PriceRespnose findCurrMemberPrice(MemberRequest request) throws Exception {
		PriceRespnose priceRespnose = new PriceRespnose();
		MemberDto memberDto = memberBiz.findMemberById(request.getMemberDto().getId());
		PriceDto priceDto = memberBiz.calculateMerberPrice(memberDto);
		priceRespnose.setPriceDto(priceDto);
		return priceRespnose;
	}

	@Override
	public MemberResponse queryAllMember(MemberRequest request) throws Exception {
		List<MemberDto> memberList = memberBiz.queryAllMember();
		MemberResponse response = new MemberResponse();
		response.setMemberDtoList(memberList);
		return response;
	}

	@Override
	public MemberResponse findMemberInfomaction(MemberRequest request) throws Exception {
		MemberInformationDto memberInfomactionDto = memberBiz.getMemberInfomation(request.getMemberDto());
		MemberResponse response = new MemberResponse();
		response.setMemberInfomationDto(memberInfomactionDto);
		return response;
	}

}
