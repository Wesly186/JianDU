package com.mialab.jiandu.model;

public class LoginRsp {

	private UserRsp userRsp;
	private OauthToken oauthToken;

	public UserRsp getUserRsp() {
		return userRsp;
	}

	public void setUserRsp(UserRsp userRsp) {
		this.userRsp = userRsp;
	}

	public OauthToken getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(OauthToken oauthToken) {
		this.oauthToken = oauthToken;
	}
}
