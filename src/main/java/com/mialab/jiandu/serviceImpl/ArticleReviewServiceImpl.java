package com.mialab.jiandu.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mialab.jiandu.mapper.ArticleReviewMapper;
import com.mialab.jiandu.model.ArticleReview;
import com.mialab.jiandu.service.ArticleReviewService;

@Service
public class ArticleReviewServiceImpl implements ArticleReviewService {

	@Autowired
	ArticleReviewMapper articleReviewMapper;

	public void insert() {

	}

	@Override
	public void insert(ArticleReview articleReview, MultipartFile articlePic)
			throws IllegalStateException, IOException {
		String newFileName = null;
		if (articlePic != null) {
			// 原始文件名称
			String pictureFile_name = articlePic.getOriginalFilename();
			// 新文件名称
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(new Date());
			newFileName = dateString
					+ "/"
					+ UUID.randomUUID().toString()
					+ pictureFile_name.substring(pictureFile_name
							.lastIndexOf("."));
			// 上传图片
			File uploadPic = new File(
					"/usr/tomcat/apache-tomcat-8.0.39/webapps/pic/"
							+ newFileName);
			if (!uploadPic.exists()) {
				uploadPic.mkdirs();
			}
			// 向磁盘写文件
			articlePic.transferTo(uploadPic);
		}
		articleReview.setPicUrl(newFileName);

		articleReviewMapper.insert(articleReview);
	}
}
