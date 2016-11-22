package com.mialab.jiandu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.model.AppVersion;
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.service.AppVersionService;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/appVersion")
public class AppVersionController {

	@Autowired
	private AppVersionService appVersionService;

	@ApiOperation(value = "获取最新版本", httpMethod = "POST")
	@RequestMapping("/checkUpdate")
	@ResponseBody
	public ResponseData checkUpdate(@RequestParam Integer versionCode)
			throws CustomException {
		AppVersion latestVersion = appVersionService
				.getLatestVersion(versionCode);
		if (latestVersion == null) {
			throw new CustomException(411, "已经是最新版本了！");
		}
		ResponseData responseData = new ResponseData(200, "获取新版本成功",
				latestVersion);
		return responseData;
	}
}
