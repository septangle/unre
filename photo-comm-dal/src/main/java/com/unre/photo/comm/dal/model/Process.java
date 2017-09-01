package com.unre.photo.comm.dal.model;

import java.util.Date;

public class Process {
	private Long id;
	
	private Long orderId;

	private String benacoScanId;

	private Long memberId;

	private Long btchNo;

	private String title;

	private String description;
	
	private String type;

	private String deleteFlag;

	private String status;

	private Integer createBy;

	private Date createTime;

	private Integer updateBy;

	private Date updateTime;

	private Long version;

	private Long uid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getOrderId() {
		return orderId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getBenacoScanId() {
		return benacoScanId;
	}

	public void setBenacoScanId(String benacoScanId) {
		this.benacoScanId = benacoScanId == null ? null : benacoScanId.trim();
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getBtchNo() {
		return btchNo;
	}

	public void setBtchNo(Long btchNo) {
		this.btchNo = btchNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}