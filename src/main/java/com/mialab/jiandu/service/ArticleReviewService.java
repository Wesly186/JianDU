package com.mialab.jiandu.service;

import org.springframework.web.multipart.MultipartFile;

import com.mialab.jiandu.model.ArticleReview;

public interface ArticleReviewService {

	void insert(ArticleReview articleReview, MultipartFile articlePic) throws Exception;
}
