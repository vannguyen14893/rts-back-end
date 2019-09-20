package com.cmc.recruitment.utils;

public class PostContentGlobal {
	private String title;
	private String body;
	private String field_email;
	private String field_recruit_date_public;
	private int field_number;
	private String field_rcid;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getField_email() {
		return field_email;
	}
	public void setField_email(String field_email) {
		this.field_email = field_email;
	}
	public String getField_recruit_date_public() {
		return field_recruit_date_public;
	}
	public void setField_recruit_date_public(String field_recruit_date_public) {
		this.field_recruit_date_public = field_recruit_date_public;
	}
	public int getField_number() {
		return field_number;
	}
	public void setField_number(int field_number) {
		this.field_number = field_number;
	}
	public String getField_rcid() {
		return field_rcid;
	}
	public void setField_rcid(String field_rcid) {
		this.field_rcid = field_rcid;
	}
	/**
	 * 
	 */
	public PostContentGlobal() {
		super();
	}
	/**
	 * @param title
	 * @param body
	 * @param field_email
	 * @param field_recruit_date_public
	 * @param field_number
	 * @param field_rcid
	 */
	public PostContentGlobal(String title, String body, String field_email, String field_recruit_date_public,
			int field_number, String field_rcid) {
		super();
		this.title = title;
		this.body = body;
		this.field_email = field_email;
		this.field_recruit_date_public = field_recruit_date_public;
		this.field_number = field_number;
		this.field_rcid = field_rcid;
	}
}
