package com.mialab.jiandu.mapper;

import java.util.List;
import java.util.Map;

import com.mialab.jiandu.model.ArticleComment;
import com.mialab.jiandu.model.ArticleCommentRsp;

public interface ArticleCommentMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(ArticleComment record);

	int insertSelective(ArticleComment record);

	ArticleComment selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ArticleComment record);

	int updateByPrimaryKey(ArticleComment record);

	List<ArticleCommentRsp> selectArticleCommentRsp(Map<String, Object> queryMap);
}