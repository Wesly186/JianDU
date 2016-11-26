package com.mialab.jiandu.service;

import java.util.List;

import com.mialab.jiandu.model.ArticleRsp;

public interface ArticleService {
	List<ArticleRsp> selectArticleUserByTime(String accessToken,
			Integer currentPage);
}
