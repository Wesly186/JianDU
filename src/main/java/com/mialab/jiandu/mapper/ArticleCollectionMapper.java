package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.ArticleCollection;

public interface ArticleCollectionMapper {

	int insert(ArticleCollection record);

	int insertSelective(ArticleCollection record);

	int updateByPrimaryKeySelective(ArticleCollection record);

	int updateByPrimaryKey(ArticleCollection record);

	void deleteByPrimaryKey(ArticleCollection record);
}