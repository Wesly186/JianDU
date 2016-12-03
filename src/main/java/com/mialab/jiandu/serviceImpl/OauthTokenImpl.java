package com.mialab.jiandu.serviceImpl;

import java.util.Date;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.mapper.OauthTokenMapper;
import com.mialab.jiandu.mapper.UserMapper;
import com.mialab.jiandu.model.LoginRsp;
import com.mialab.jiandu.model.OauthToken;
import com.mialab.jiandu.model.User;
import com.mialab.jiandu.service.OauthTokenService;

@Service
public class OauthTokenImpl implements OauthTokenService {

	@Autowired
	private OauthTokenMapper oauthTokenMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public OauthToken getOauthTokenByAccessToken(String accessToken) {
		return oauthTokenMapper.getOauthTokenByAccessToken(accessToken);
	}

	@Override
	public LoginRsp getOauthInfo(String phone, String password)
			throws OAuthSystemException, CustomException {
		// 验证手机号和密码是否正确
		password = new MD5Generator().generateValue(password);
		User user = userMapper.selectByPrimaryKey(phone);
		if (user == null || !user.getPassword().equalsIgnoreCase(password)) {
			throw new CustomException(410, "用户名或密码错误");
		}
		// 生成token信息并添加到oauth_token表中
		OauthToken oauthTokenNew = null;
		OauthToken oauthTokenExist = oauthTokenMapper.selectByPrimaryKey(phone);
		// 不存在token信息
		if (oauthTokenExist == null) {
			oauthTokenNew = generateOauthToken(phone);
			oauthTokenMapper.insert(oauthTokenNew);
		} else {
			// 存在但是refreshToken过期
			if (oauthTokenExist.getRefreshTokenExpires().getTime() < new Date()
					.getTime()) {
				oauthTokenNew = generateOauthToken(phone);
				oauthTokenMapper.updateByPrimaryKey(oauthTokenNew);
			} else {
				// accessToken过期
				if (oauthTokenExist.getAccessTokenExpires().getTime() < new Date()
						.getTime()) {
					oauthTokenNew = generateOauthToken(phone);
					oauthTokenNew.setRefreshToken(oauthTokenExist
							.getRefreshToken());
					oauthTokenNew.setRefreshTokenExpires(oauthTokenExist
							.getRefreshTokenExpires());
					oauthTokenMapper.updateByPrimaryKey(oauthTokenNew);
				} else {
					oauthTokenNew = oauthTokenMapper.selectByPrimaryKey(phone);
				}
			}
		}
		// 查询User信息，生成响应
		LoginRsp loginRsp = new LoginRsp();
		loginRsp.setUserRsp(userMapper.selectLoginInfoByPrimaryKey(phone));
		loginRsp.setOauthToken(oauthTokenNew);

		return loginRsp;
	}

	@Override
	public OauthToken getNewAccessToken(String refreshToken)
			throws CustomException, OAuthSystemException {
		OauthToken oauthToken = oauthTokenMapper
				.selectByRefreshToken(refreshToken);
		if (oauthToken == null
				|| oauthToken.getRefreshTokenExpires().getTime() < new Date()
						.getTime()) {
			throw new CustomException(403, "RefreshToken过期，请重新登陆！");
		}
		if (oauthToken.getAccessTokenExpires().getTime() < new Date().getTime()) {
			OAuthIssuerImpl authIssuerImpl = new OAuthIssuerImpl(
					new MD5Generator());
			String accessToken = authIssuerImpl.accessToken();
			oauthToken.setAccessToken(accessToken);
			oauthToken.setAccessTokenExpires(new Date(
					new Date().getTime() + 1000 * 60 * 30));
			oauthTokenMapper.updateByPrimaryKey(oauthToken);
		}

		return oauthToken;
	}

	private OauthToken generateOauthToken(String phone)
			throws OAuthSystemException {
		OauthToken oauthToken = new OauthToken();
		OAuthIssuerImpl authIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		String accessToken = authIssuerImpl.accessToken();
		String refreshToken = authIssuerImpl.refreshToken();

		oauthToken.setAccessToken(accessToken);
		oauthToken.setAccessTokenExpires(new Date(
				new Date().getTime() + 1000 * 60 * 30));
		oauthToken.setPhone(phone);
		oauthToken.setRefreshToken(refreshToken);
		oauthToken.setRefreshTokenExpires(new Date(new Date().getTime() + 1000
				* 60 * 60 * 24 * 30L));

		return oauthToken;
	}
}
