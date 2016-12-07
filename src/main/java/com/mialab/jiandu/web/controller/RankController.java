package com.mialab.jiandu.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mialab.jiandu.model.Rank;
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/rank")
public class RankController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "根据阅读数排名", httpMethod = "GET")
	@RequestMapping("/getRankByReads")
	@ResponseBody
	public ResponseData getRankByReads(String accessToken) throws Exception {
		List<Rank> ranks = userService.getRankByReads();
		return new ResponseData(200, null, ranks);
	}

	@ApiOperation(value = "根据发表文章数排名", httpMethod = "GET")
	@RequestMapping("/getRankByContributions")
	@ResponseBody
	public ResponseData getRankByContributions(String accessToken)
			throws Exception {
		List<Rank> ranks = userService.getRankByContributions();
		return new ResponseData(200, null, ranks);
	}
}
