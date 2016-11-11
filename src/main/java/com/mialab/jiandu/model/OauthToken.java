package com.mialab.jiandu.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OauthToken {
	
	@JsonIgnore
	private String phone;

	private String accessToken;

	private Date accessTokenExpires;

	private String refreshToken;

	private Date refreshTokenExpires;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken == null ? null : accessToken.trim();
	}

	public Date getAccessTokenExpires() {
		return accessTokenExpires;
	}

	public void setAccessTokenExpires(Date accessTokenExpires) {
		this.accessTokenExpires = accessTokenExpires;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken == null ? null : refreshToken.trim();
	}

	public Date getRefreshTokenExpires() {
		return refreshTokenExpires;
	}

	public void setRefreshTokenExpires(Date refreshTokenExpires) {
		this.refreshTokenExpires = refreshTokenExpires;
	}
}