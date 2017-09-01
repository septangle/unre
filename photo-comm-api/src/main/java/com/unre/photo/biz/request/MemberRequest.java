package com.unre.photo.biz.request;

import com.unre.photo.biz.dto.MemberDto;

@SuppressWarnings("serial")
public class MemberRequest extends BaseRequest {

	private MemberDto MemberDto;

	public MemberDto getMemberDto() {
		return MemberDto;
	}

	public void setMemberDto(MemberDto MemberDto) {
		this.MemberDto = MemberDto;
	}

}
