package com.mialab.jiandu.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.mapper.ArticleCollectionMapper;
import com.mialab.jiandu.model.ArticleCollection;
import com.mialab.jiandu.service.ArticleCollectionService;

@Service
public class ArticleCollectionServiceImpl implements ArticleCollectionService {

	@Autowired
	private ArticleCollectionMapper articleCollectionMapper;

	@Override
	public void deleteByPrimaryKey(ArticleCollection articleCollection) {
		articleCollectionMapper.deleteByPrimaryKey(articleCollection);
	}

	@Override
	public void insert(ArticleCollection articleCollection) {
		articleCollectionMapper.insert(articleCollection);
	}

}
