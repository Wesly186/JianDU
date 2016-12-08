package com.mialab.jiandu.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.model.ArticleCollection;
import com.mialab.jiandu.model.ArticleComment;
import com.mialab.jiandu.model.ArticleCommentRsp;
import com.mialab.jiandu.model.ArticleRead;
import com.mialab.jiandu.model.ArticleReview;
import com.mialab.jiandu.model.ArticleRsp;
import com.mialab.jiandu.model.OauthToken;
import com.mialab.jiandu.model.ResponseData;
import com.mialab.jiandu.service.ArticleCollectionService;
import com.mialab.jiandu.service.ArticleCommentService;
import com.mialab.jiandu.service.ArticleReadService;
import com.mialab.jiandu.service.ArticleReviewService;
import com.mialab.jiandu.service.ArticleService;
import com.mialab.jiandu.service.OauthTokenService;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private OauthTokenService oauthTokenService;
	@Autowired
	private ArticleReviewService articleReviewService;
	@Autowired
	private ArticleCollectionService articleCollectionService;
	@Autowired
	private ArticleCommentService articleCommentService;
	@Autowired
	private ArticleReadService articleReadService;

	@ApiOperation(value = "获取轮播图列表", httpMethod = "POST")
	@RequestMapping("/getBanners")
	@ResponseBody
	public ResponseData getBanner(String accessToken) throws CustomException {
		List<ArticleRsp> articleUserByTime = articleService
				.getBanners(accessToken);
		ResponseData responseData = new ResponseData(200, null,
				articleUserByTime);
		return responseData;
	}

	@ApiOperation(value = "根据时间顺序后的article列表", httpMethod = "POST")
	@RequestMapping("/getArticleByTime")
	@ResponseBody
	public ResponseData getArticleByTime(String accessToken,
			@RequestParam Integer currentPage) throws CustomException {
		List<ArticleRsp> articleUserByTime = articleService
				.selectArticleUserByTime(accessToken, currentPage);
		ResponseData responseData = new ResponseData(200, "获取article成功",
				articleUserByTime);
		return responseData;
	}

	@ApiOperation(value = "综合时间，热度排序", httpMethod = "POST")
	@RequestMapping("/getArticleSynthetically")
	@ResponseBody
	public ResponseData getArticleSynthetically(String accessToken,
			@RequestParam Integer currentPage) throws CustomException {
		List<ArticleRsp> articleUserByTime = articleService
				.selectArticleSynthetically(accessToken, currentPage);
		ResponseData responseData = new ResponseData(200, "获取article成功",
				articleUserByTime);
		return responseData;
	}

	@ApiOperation(value = "本周热门", httpMethod = "POST")
	@RequestMapping("/getArticleWeekHot")
	@ResponseBody
	public ResponseData getArticleWeekHot(String accessToken,
			@RequestParam Integer currentPage) throws CustomException {
		List<ArticleRsp> articleUserByTime = articleService.getArticleWeekHot(
				accessToken, currentPage);
		ResponseData responseData = new ResponseData(200, "获取article成功",
				articleUserByTime);
		return responseData;
	}

	@ApiOperation(value = "我的收藏", httpMethod = "POST")
	@RequestMapping("/getArticleCollection")
	@ResponseBody
	public ResponseData getArticleCollection(String accessToken,
			@RequestParam Integer currentPage) throws CustomException {
		List<ArticleRsp> articleUserByTime = articleService
				.getArticleCollection(accessToken, currentPage);
		ResponseData responseData = new ResponseData(200, "获取article成功",
				articleUserByTime);
		return responseData;
	}

	@ApiOperation(value = "我的阅历", httpMethod = "POST")
	@RequestMapping("/getArticleReads")
	@ResponseBody
	public ResponseData getArticleReads(String accessToken,
			@RequestParam Integer currentPage) throws CustomException {
		List<ArticleRsp> articleUserByTime = articleService.getArticleReads(
				accessToken, currentPage);
		ResponseData responseData = new ResponseData(200, "获取article成功",
				articleUserByTime);
		return responseData;
	}

	@ApiOperation(value = "用户投稿供审核", httpMethod = "POST")
	@RequestMapping("/publishArticle")
	@ResponseBody
	public ResponseData publishArticle(String accessToken,
			MultipartFile articlePic, String articleTitle, String articleDesc,
			String articleAddress) throws Exception {

		OauthToken oauthToken = oauthTokenService
				.getOauthTokenByAccessToken(accessToken);

		ArticleReview articleReview = new ArticleReview();
		articleReview.setTitle(articleTitle);
		articleReview.setBriefIntro(articleDesc);
		articleReview.setState(false);
		articleReview.setUserPhone(oauthToken.getPhone());
		articleReview.setArticleUrl(articleAddress);

		articleReviewService.insert(articleReview, articlePic);

		ResponseData responseData = new ResponseData(200, "获取新版本成功", null);
		return responseData;
	}

	@ApiOperation(value = "收藏、取消收藏文章", httpMethod = "POST")
	@RequestMapping("/collectArticle")
	@ResponseBody
	public ResponseData collectArticle(String accessToken, Integer articleId,
			Boolean collect) throws Exception {

		OauthToken oauthToken = oauthTokenService
				.getOauthTokenByAccessToken(accessToken);

		ArticleCollection articleCollection = new ArticleCollection();
		articleCollection.setArticleId(articleId);
		articleCollection.setPhone(oauthToken.getPhone());
		if (collect) {
			articleCollection.setCollectTime(new Date());
			articleCollectionService.insert(articleCollection);
		} else {
			articleCollectionService.deleteByPrimaryKey(articleCollection);
		}
		ResponseData responseData = new ResponseData(200, "成功", null);
		return responseData;
	}

	@ApiOperation(value = "获取评论列表", httpMethod = "POST")
	@RequestMapping("/getComments")
	@ResponseBody
	public ResponseData getComments(Integer articleId, int currentPage)
			throws Exception {

		List<ArticleCommentRsp> comments = articleCommentService.getComments(
				articleId, currentPage);
		return new ResponseData(200, null, comments);
	}

	@ApiOperation(value = "评论文章", httpMethod = "POST")
	@RequestMapping("/doComment")
	@ResponseBody
	public ResponseData doComment(String accessToken, int articleId,
			String comment) throws Exception {
		OauthToken oauthToken = oauthTokenService
				.getOauthTokenByAccessToken(accessToken);

		ArticleComment articleComment = new ArticleComment();
		articleComment.setArticleId(articleId);
		articleComment.setComment(comment);
		articleComment.setPhone(oauthToken.getPhone());
		articleComment.setPublishTime(new Date());
		articleCommentService.doComment(articleComment);
		return new ResponseData(200, null, null);
	}

	@ApiOperation(value = "添加到已经阅读列表", httpMethod = "POST")
	@RequestMapping("/add2Read")
	@ResponseBody
	public ResponseData add2Read(String accessToken, int articleId)
			throws Exception {

		OauthToken oauthToken = oauthTokenService
				.getOauthTokenByAccessToken(accessToken);

		ArticleRead articleRead = new ArticleRead();
		articleRead.setArticleId(articleId);
		articleRead.setPhone(oauthToken.getPhone());
		articleRead.setReadTime(new Date());

		articleReadService.insert(articleRead);
		return new ResponseData(200, null, null);
	}

	@ApiOperation(value = "根据时间顺序后的article列表", httpMethod = "GET")
	@RequestMapping("/searchArticle")
	@ResponseBody
	public ResponseData searchArticle(String accessToken,
			@RequestParam String keyword, @RequestParam Integer currentPage)
			throws Exception {

		List<ArticleRsp> articleUserByTime = articleService
				.selectArticleBykeyword(accessToken, keyword, currentPage);
		ResponseData responseData = new ResponseData(200, "获取article成功",
				articleUserByTime);
		return responseData;
	}
}
