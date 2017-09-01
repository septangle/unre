 package com.unre.photo.biz.response;

import java.util.List;

import com.unre.photo.biz.dto.MemberDto;

@SuppressWarnings("serial")
public class MemberResponse extends BaseResponse {

	private MemberDto MemberDto;

	private List<MemberDto> MemberDtoList;

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

}
