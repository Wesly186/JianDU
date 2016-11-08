package com.mialab.jiandu.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.mapper.OauthTokenCustomMapper;
import com.mialab.jiandu.model.OauthTokenCustom;
import com.mialab.jiandu.service.OauthTokenService;

@Service
public class OauthTokenImpl implements OauthTokenService {

	@Autowired
	private OauthTokenCustomMapper oauthTokenCustomMapper;

	@Override
	public OauthTokenCustom getOauthTokenByAccessToken(String accessToken) {
		return oauthTokenCustomMapper.getOauthTokenByAccessToken(accessToken);
	}
}
