package com.mialab.jiandu.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.conf.GlobalConf;
import com.mialab.jiandu.mapper.ArticleCommentMapper;
import com.mialab.jiandu.model.ArticleComment;
import com.mialab.jiandu.model.ArticleCommentRsp;
import com.mialab.jiandu.service.ArticleCommentService;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

	@Autowired
	private ArticleCommentMapper articleCommentMapper;

	@Override
	public List<ArticleCommentRsp> getComments(int articleId, int currentPage) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("articleId", articleId);
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		return articleCommentMapper.selectArticleCommentRsp(queryMap);
	}

	@Override
	public void doComment(ArticleComment articleComment) {
		articleCommentMapper.insertSelective(articleComment);
	}
}
