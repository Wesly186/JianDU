package com.mialab.jiandu.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.model.ArticleRsp;
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.service.ArticleService;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@ApiOperation(value = "根据时间顺序后的article列表", httpMethod = "POST")
	@RequestMapping("/getArticleByTime")
	@ResponseBody
	public ResponseData getArticleByTime(String accessToken,
			@RequestParam Integer currentPage) throws CustomException {
		List<ArticleRsp> articleUserByTime = articleService
				.selectArticleUserByTime(accessToken, currentPage);
		ResponseData responseData = new ResponseData(200, "获取新版本成功",
				articleUserByTime);
		return responseData;
	}
}
