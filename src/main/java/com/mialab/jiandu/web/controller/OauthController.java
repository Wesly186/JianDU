package com.mialab.jiandu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mialab.jiandu.model.LoginRsp;
import com.mialab.jiandu.model.OauthToken;
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.service.OauthTokenService;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/oauth")
public class OauthController {

	@Autowired
	private OauthTokenService oauthTokenService;

	@ApiOperation(value = "登陆", httpMethod = "POST")
	@RequestMapping("/login")
	@ResponseBody
	public ResponseData login(@RequestParam String phone,
			@RequestParam String password) throws Exception {
		LoginRsp loginRsp = oauthTokenService.getOauthInfo(phone, password);
		return new ResponseData(200, "登陆成功", loginRsp);
	}

	@ApiOperation(value = "刷新token", httpMethod = "POST")
	@RequestMapping("/refreshAccessToken")
	@ResponseBody
	public ResponseData refreshAccessToken(@RequestParam String refreshToken)
			throws Exception {
		OauthToken oauthToken = oauthTokenService
				.getNewAccessToken(refreshToken);
		return new ResponseData(200, "更新AccessToken成功", oauthToken);
	}
}
