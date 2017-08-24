package com.unre.photo.biz.dto;

import java.util.List;
import java.io.File;

public class PanoramaEngineDto {

	private String apiBaseUrl;

	private String apiKey;

	private String benacoScanId;

	private String title;

	private List<File> files;

	private String previewUrl;
	
	private String scanStatus;

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

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
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

}
