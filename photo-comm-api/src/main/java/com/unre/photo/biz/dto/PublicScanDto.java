package com.unre.photo.biz.dto;

import java.util.Date;

public class PublicScanDto {
	
	private Long memberId;
	
	private String memberName;

	private String benacoScanId;

	private String description;
	
	private String thumbImagePath;
	
	private Date createTime;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBenacoScanId() {
		return benacoScanId;
	}

	public void setBenacoScanId(String benacoScanId) {
		this.benacoScanId = benacoScanId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbImagePath() {
		return thumbImagePath;
	}

	public void setThumbImagePath(String thumbImagePath) {
		this.thumbImagePath = thumbImagePath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	

}
