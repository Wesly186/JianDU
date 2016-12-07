package com.mialab.jiandu.service;

import java.util.List;

import com.mialab.jiandu.model.ArticleRsp;

public interface ArticleService {

	List<ArticleRsp> selectArticleUserByTime(String accessToken,
			Integer currentPage);

	List<ArticleRsp> selectArticleSynthetically(String accessToken,
			Integer currentPage);

	List<ArticleRsp> getArticleWeekHot(String accessToken, Integer currentPage);

	List<ArticleRsp> getArticleCollection(String accessToken,
			Integer currentPage);

	List<ArticleRsp> getArticleReads(String accessToken, Integer currentPage);

	List<ArticleRsp> getBanners(String accessToken);
}
