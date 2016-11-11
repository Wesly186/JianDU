package com.mialab.jiandu.model;

public class LoginRsp {

	private User user;
	private OauthToken oauthToken;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OauthToken getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(OauthToken oauthToken) {
		this.oauthToken = oauthToken;
	}

}
