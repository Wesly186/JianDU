package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.OauthToken;

public interface OauthTokenMapper {

	int deleteByPrimaryKey(String phone);

	int insert(OauthToken record);

	int insertSelective(OauthToken record);

	OauthToken selectByPrimaryKey(String phone);

	int updateByPrimaryKeySelective(OauthToken record);

	int updateByPrimaryKey(OauthToken record);

	OauthToken getOauthTokenByAccessToken(String accessToken);

	OauthToken selectByRefreshToken(String refreshToken);
}