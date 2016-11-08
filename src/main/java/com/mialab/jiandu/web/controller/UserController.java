package com.mialab.jiandu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/register")
	@ResponseBody
	public ResponseData register() throws Exception {
		
		//ResponseData responseData = new ResponseData(200, "注册成功", user);
		return null;
	}

	@RequestMapping("/getValidateCode")
	@ResponseBody
	public ResponseData getValidateCode(@RequestParam String phone,
			@RequestParam String business) throws Exception {
		if (phone.length() != 11) {
			throw new CustomException(400, "参数不合法");
		}
		userService.getValidateCode(phone, business);
		ResponseData responseData = new ResponseData(200, "验证码发送成功", null);
		return responseData;
	}
}
