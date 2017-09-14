package com.unre.photo.framework.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {

	private byte[] rawData;
	private HttpServletRequest request;
	private ResettableServletInputStream servletStream;

	public ResettableStreamHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
		this.servletStream = new ResettableServletInputStream();
	}

	public byte[] getRawData() {
		return rawData;
	}

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	public void resetInputStream() {
		servletStream.stream = new ByteArrayInputStream(rawData);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (rawData == null) {
			rawData = IOUtils.toByteArray(this.request.getInputStream());
			servletStream.stream = new ByteArrayInputStream(rawData);
		}
		return servletStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (rawData == null) {
			rawData = IOUtils.toByteArray(this.request.getReader(),Charset.forName("utf-8").toString());
			servletStream.stream = new ByteArrayInputStream(rawData);
		}
		return new BufferedReader(new InputStreamReader(servletStream));
	}

	private class ResettableServletInputStream extends ServletInputStream {

		private InputStream stream;

		@Override
		public int read() throws IOException {
			return stream.read();
		}

	}

}
