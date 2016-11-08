package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.OauthTokenCustom;

public interface OauthTokenCustomMapper {
	
	OauthTokenCustom getOauthTokenByAccessToken(String accessToken);
}
