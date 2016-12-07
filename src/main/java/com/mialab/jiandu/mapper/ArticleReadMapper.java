package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.ArticleRead;

public interface ArticleReadMapper {

	int insert(ArticleRead record);

	int insertSelective(ArticleRead record);

	int updateByPrimaryKeySelective(ArticleRead record);

	int updateByPrimaryKey(ArticleRead record);
}