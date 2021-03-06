package com.unre.photo.comm.dal.model;

import java.util.Date;

public class Photo {
    private Long id;

    private Long orderId;

    private Long panoramaId;

    private String imagePath;

    private String thumbImagePath;

    private String isDeleted;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;
    
    private Panorama panorama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPanoramaId() {
        return panoramaId;
    }

    public void setPanoramaId(Long panoramaId) {
        this.panoramaId = panoramaId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    public String getThumbImagePath() {
        return thumbImagePath;
    }

    public void setThumbImagePath(String thumbImagePath) {
        this.thumbImagePath = thumbImagePath == null ? null : thumbImagePath.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
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

	public Panorama getPanorama() {
		return panorama;
	}

	public void setPanorama(Panorama panorama) {
		this.panorama = panorama;
	}
    
    
}