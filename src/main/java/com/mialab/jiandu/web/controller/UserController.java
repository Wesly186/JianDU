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
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.model.User;
import com.mialab.jiandu.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

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
}
