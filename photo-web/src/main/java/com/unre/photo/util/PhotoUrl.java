package com.unre.photo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("photoUrl")
public  class PhotoUrl {
	
	@Value("${photo.keyId}")
	private String key;    
	
	@Value("${photo.serviceUrl}")
    private String url;
	
	@Value("${photo.path}")
	private String path;
    
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
    
    
	
    
    
}
