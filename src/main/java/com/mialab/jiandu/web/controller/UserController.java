package com.mialab.jiandu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.model.OauthToken;
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.model.User;
import com.mialab.jiandu.model.UserRsp;
import com.mialab.jiandu.service.OauthTokenService;
import com.mialab.jiandu.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private OauthTokenService oauthTokenService;

	@ApiOperation(value = "注册", httpMethod = "POST")
	@RequestMapping("/register")
	@ResponseBody
	public ResponseData register(@Validated User user, BindingResult result,
			@RequestParam String validationCode) throws Exception {
		// 参数校验
		if (result.getErrorCount() != 0) {
			throw new CustomException(400, "参数不合法");
		}
		// 注册操作
		userService.register(user, validationCode);
		ResponseData responseData = new ResponseData(200, "注册成功", null);
		return responseData;
	}

	@ApiOperation(value = "获取验证码", httpMethod = "POST")
	@RequestMapping("/getValidationCode")
	@ResponseBody
	public ResponseData getValidationCode(@RequestParam String phone,
			@RequestParam String business) throws Exception {
		if (phone.length() != 11) {
			throw new CustomException(400, "参数不合法");
		}
		userService.getValidationCode(phone, business);
		ResponseData responseData = new ResponseData(200, "验证码发送成功", null);
		return responseData;
	}

	@ApiOperation(value = "更新用户信息", httpMethod = "POST")
	@RequestMapping("/updateProfile")
	@ResponseBody
	public ResponseData updateProfile(String accessToken,
			MultipartFile headPic, String username, String blogAddress,
			String introduction, String sex, String job) throws Exception {
		User user = new User();
		user.setUsername(username);
		user.setBlogAddress(blogAddress);
		user.setIntroduction(introduction);
		user.setSex(sex);
		user.setJob(job);

		user = userService.updateProfile(accessToken, headPic, user);
		ResponseData responseData = new ResponseData(200, "修改成功", user);

		return responseData;
	}

	@ApiOperation(value = "更新密码", httpMethod = "POST")
	@RequestMapping("/updatePassword")
	@ResponseBody
	public ResponseData updatePassword(String phone, String oldPassword,
			String newPassword) throws Exception {
		userService.updatePassword(phone, oldPassword, newPassword);
		ResponseData responseData = new ResponseData(200, "修改成功", null);
		return responseData;
	}

	@ApiOperation(value = "根据accessToken获取用户信息", httpMethod = "POST")
	@RequestMapping("/getUserInfoByToken")
	@ResponseBody
	public ResponseData getUserInfoByToken(String accessToken) throws Exception {

		OauthToken oauthToken = oauthTokenService
				.getOauthTokenByAccessToken(accessToken);

		UserRsp userRsp = userService.getUserInfoByPhone(oauthToken.getPhone());
		return new ResponseData(200, null, userRsp);
	}
}
