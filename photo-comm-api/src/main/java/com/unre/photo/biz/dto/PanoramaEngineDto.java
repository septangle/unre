package com.unre.photo.biz.dto;

import java.util.List;
import java.io.File;

public class PanoramaEngineDto {
	private Long id;

	private String apiBaseUrl;

	private String apiKey;

	private String benacoScanId;

	private String title;

	private List<ImageInfoDto> imageInfoList;

	private String previewUrl;
	
	private String scanStatus;
	
	private Long uid;
	
	private Long orderId;
	
	private String number;
	
	private List<File> fileThumb;
	
    private String privacy;


	public List<File> getFileThumb() {
		return fileThumb;
	}

	public void setFileThumb(List<File> fileThumb) {
		this.fileThumb = fileThumb;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getBenacoScanId() {
		return benacoScanId;
	}

	public void setBenacoScanId(String benacoScanId) {
		this.benacoScanId = benacoScanId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ImageInfoDto> getImageInfoList() {
		return imageInfoList;
	}

	public void setImageInfoList(List<ImageInfoDto> imageInfoList) {
		this.imageInfoList = imageInfoList;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getScanStatus() {
		return scanStatus;
	}

	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	

}
