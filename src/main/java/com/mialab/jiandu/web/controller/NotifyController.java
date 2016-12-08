package com.mialab.jiandu.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mialab.jiandu.model.Message;
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.service.NotifyService;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/notify")
public class NotifyController {

	@Autowired
	private NotifyService notifyService;

	@ApiOperation(value = "获取消息列表", httpMethod = "GET")
	@RequestMapping("/getMessages")
	@ResponseBody
	public ResponseData getMessages(@RequestParam String accessToken,
			@RequestParam int currentPage) throws Exception {
		List<Message> messages = notifyService.getMessages(accessToken,
				currentPage);
		return new ResponseData(200, messages.size()+"", messages);
	}

	@ApiOperation(value = "获取活动列表", httpMethod = "GET")
	@RequestMapping("/getActivities")
	@ResponseBody
	public ResponseData getActivities(String accessToken) throws Exception {

		return new ResponseData(200, null, null);
	}
}
