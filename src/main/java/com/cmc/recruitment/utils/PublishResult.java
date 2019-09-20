package com.cmc.recruitment.utils;

public class PublishResult {
	private boolean status;
	private String url;
	private String message;
	private PostContentGlobal data;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PostContentGlobal getData() {
		return data;
	}
	public void setData(PostContentGlobal data) {
		this.data = data;
	}
	/**
	 * 
	 */
	public PublishResult() {
		super();
	}
	/**
	 * @param status
	 * @param url
	 * @param message
	 * @param data
	 */
	public PublishResult(boolean status, String url, String message, PostContentGlobal data) {
		super();
		this.status = status;
		this.url = url;
		this.message = message;
		this.data = data;
	}
	
	
}
