package com.mialab.jiandu.service;

import com.mialab.jiandu.model.OauthTokenCustom;

public interface OauthTokenService {

	OauthTokenCustom getOauthTokenByAccessToken(String accessToken);
}
