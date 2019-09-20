package com.cmc.recruitment.utils;

import com.cmc.recruitment.entity.AccountGlobal;

public class TokenGlobal {
	private AccountGlobal accountGlobal;
	private String csrf_token;
	private String logout_token;
	public AccountGlobal getAccountGlobal() {
		return accountGlobal;
	}
	public void setAccountGlobal(AccountGlobal accountGlobal) {
		this.accountGlobal = accountGlobal;
	}
	public String getCsrf_token() {
		return csrf_token;
	}
	public void setCsrf_token(String csrf_token) {
		this.csrf_token = csrf_token;
	}
	public String getLogout_token() {
		return logout_token;
	}
	public void setLogout_token(String logout_token) {
		this.logout_token = logout_token;
	}
	/**
	 * @param accountGlobal
	 * @param csrf_token
	 * @param logout_token
	 */
	public TokenGlobal(AccountGlobal accountGlobal, String csrf_token, String logout_token) {
		super();
		this.accountGlobal = accountGlobal;
		this.csrf_token = csrf_token;
		this.logout_token = logout_token;
	}
	/**
	 * 
	 */
	public TokenGlobal() {
		super();
	}
	
	
}
