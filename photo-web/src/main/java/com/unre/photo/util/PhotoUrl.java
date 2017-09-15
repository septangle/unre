package com.unre.photo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("photoUrl")
public  class PhotoUrl {
	
	@Value("${photo.keyId}")
	private String key;    
	
	@Value("${photo.serviceUrl}")
    private String url;
	
	@Value("${photo.photopath}")
	private String photoPath;
	
	@Value("${photo.paramopath}")
	private String paramoPath;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getParamoPath() {
		return paramoPath;
	}

	public void setParamoPath(String paramoPath) {
		this.paramoPath = paramoPath;
	}

}
