package com.mialab.jiandu.service;

import java.util.List;

import com.mialab.jiandu.model.ArticleComment;
import com.mialab.jiandu.model.ArticleCommentRsp;

public interface ArticleCommentService {
	
	List<ArticleCommentRsp> getComments(int articleId, int currentPage);

	void doComment(ArticleComment articleComment);
}
