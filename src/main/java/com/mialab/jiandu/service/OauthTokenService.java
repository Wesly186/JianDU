package com.mialab.jiandu.service;

import com.mialab.jiandu.model.LoginRsp;
import com.mialab.jiandu.model.OauthToken;

public interface OauthTokenService {

	OauthToken getOauthTokenByAccessToken(String accessToken) throws Exception;

	LoginRsp getOauthInfo(String phone, String password) throws Exception;

	OauthToken getNewAccessToken(String refreshToken) throws Exception;
}
