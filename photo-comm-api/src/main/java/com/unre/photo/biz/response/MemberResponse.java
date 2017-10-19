 package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.dto.MemberInformationDto;

@SuppressWarnings("serial")
public class MemberResponse extends BaseResponse {
	private String token;

	private MemberDto MemberDto;

	private List<MemberDto> MemberDtoList;
	
	private MemberInformationDto memberInfomationDto;

	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public MemberDto getMemberDto() {
		return MemberDto;
	}

	public void setMemberDto(MemberDto MemberDto) {
		this.MemberDto = MemberDto;
	}

	public List<MemberDto> getMemberDtoList() {
		return MemberDtoList;
	}

	public void setMemberDtoList(List<MemberDto> MemberDtoList) {
		this.MemberDtoList = MemberDtoList;
	}

	public MemberInformationDto getMemberInfomationDto() {
		return memberInfomationDto;
	}

	public void setMemberInfomationDto(MemberInformationDto memberInfomationDto) {
		this.memberInfomationDto = memberInfomationDto;
	}

}
