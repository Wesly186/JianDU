package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.ArticleReview;

public interface ArticleReviewMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(ArticleReview record);

	int insertSelective(ArticleReview record);

	ArticleReview selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ArticleReview record);

	int updateByPrimaryKey(ArticleReview record);
}