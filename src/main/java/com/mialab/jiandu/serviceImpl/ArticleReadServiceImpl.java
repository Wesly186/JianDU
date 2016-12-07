package com.mialab.jiandu.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.mapper.ArticleReadMapper;
import com.mialab.jiandu.model.ArticleRead;
import com.mialab.jiandu.service.ArticleReadService;

@Service
public class ArticleReadServiceImpl implements ArticleReadService {

	@Autowired
	private ArticleReadMapper articleReadMapper;

	@Override
	public void insert(ArticleRead articleRead) {
		articleReadMapper.insert(articleRead);
	}
}
