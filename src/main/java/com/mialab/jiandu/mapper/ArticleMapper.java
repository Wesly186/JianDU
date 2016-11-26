package com.mialab.jiandu.mapper;

import java.util.List;
import java.util.Map;

import com.mialab.jiandu.model.Article;
import com.mialab.jiandu.model.ArticleRsp;

public interface ArticleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
    
    List<ArticleRsp> selectArticleUserByTime(Map<String, Object> queryMap);
}