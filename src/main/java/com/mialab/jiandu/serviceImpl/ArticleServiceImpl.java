package com.mialab.jiandu.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.conf.GlobalConf;
import com.mialab.jiandu.mapper.ArticleMapper;
import com.mialab.jiandu.model.ArticleRsp;
import com.mialab.jiandu.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public List<ArticleRsp> selectArticleUserByTime(String accessToken,
			Integer currentPage) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("accessToken", accessToken);
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		return articleMapper.selectArticleUserByTime(queryMap);
	}

}
