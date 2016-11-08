package com.mialab.jiandu.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public ResponseData register() throws Exception {
		User user = new User();
		user.setPhone("18625210821");
		user.setPassword("123456");
		user.setUsername("wesly186");
		user.setSalt("fdgdfg");
		user.setRegisterTime(new Date());
		userService.register(user);
		ResponseData responseData = new ResponseData(200, "注册成功", user);
		return responseData;
	}
}
