package com.mialab.jiandu.service;

import com.mialab.jiandu.model.ArticleCollection;

public interface ArticleCollectionService {

	void deleteByPrimaryKey(ArticleCollection articleCollection);

	void insert(ArticleCollection articleCollection);
}
