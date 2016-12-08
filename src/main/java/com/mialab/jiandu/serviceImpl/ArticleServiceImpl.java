package com.mialab.jiandu.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.conf.GlobalConf;
import com.mialab.jiandu.mapper.ArticleMapper;
import com.mialab.jiandu.mapper.OauthTokenMapper;
import com.mialab.jiandu.model.ArticleRsp;
import com.mialab.jiandu.model.OauthToken;
import com.mialab.jiandu.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private OauthTokenMapper oauthTokenMapper;

	@Override
	public List<ArticleRsp> selectArticleUserByTime(String accessToken,
			Integer currentPage) {
		OauthToken oauthToken = null;
		if (accessToken != null) {
			oauthToken = oauthTokenMapper
					.getOauthTokenByAccessToken(accessToken);
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (oauthToken != null) {
			queryMap.put("phone", oauthToken.getPhone());
		} else {
			queryMap.put("phone", "88888888");
		}
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		return articleMapper.selectArticleUserByTime(queryMap);
	}

	@Override
	public List<ArticleRsp> selectArticleSynthetically(String accessToken,
			Integer currentPage) {
		OauthToken oauthToken = null;
		if (accessToken != null) {
			oauthToken = oauthTokenMapper
					.getOauthTokenByAccessToken(accessToken);
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (oauthToken != null) {
			queryMap.put("phone", oauthToken.getPhone());
		} else {
			queryMap.put("phone", "88888888");
		}
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		return articleMapper.selectArticleSynthetically(queryMap);
	}

	@Override
	public List<ArticleRsp> getArticleWeekHot(String accessToken,
			Integer currentPage) {
		OauthToken oauthToken = null;
		if (accessToken != null) {
			oauthToken = oauthTokenMapper
					.getOauthTokenByAccessToken(accessToken);
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (oauthToken != null) {
			queryMap.put("phone", oauthToken.getPhone());
		} else {
			queryMap.put("phone", "88888888");
		}
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		return articleMapper.selectArticleWeekHot(queryMap);
	}

	@Override
	public List<ArticleRsp> getArticleCollection(String accessToken,
			Integer currentPage) {
		OauthToken oauthToken = oauthTokenMapper
				.getOauthTokenByAccessToken(accessToken);

		Map<String, Object> queryMap = new HashMap<String, Object>();

		queryMap.put("phone", oauthToken.getPhone());
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		return articleMapper.getArticleCollection(queryMap);
	}

	@Override
	public List<ArticleRsp> getArticleReads(String accessToken,
			Integer currentPage) {
		OauthToken oauthToken = oauthTokenMapper
				.getOauthTokenByAccessToken(accessToken);

		Map<String, Object> queryMap = new HashMap<String, Object>();

		queryMap.put("phone", oauthToken.getPhone());
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		return articleMapper.getArticleReads(queryMap);
	}

	@Override
	public List<ArticleRsp> getBanners(String accessToken) {
		OauthToken oauthToken = null;
		if (accessToken != null) {
			oauthToken = oauthTokenMapper
					.getOauthTokenByAccessToken(accessToken);
		}

		String phone = null;
		if (oauthToken != null) {
			phone = oauthToken.getPhone();
		} else {
			phone = "88888888";
		}

		return articleMapper.selectBanners(phone);
	}

	@Override
	public List<ArticleRsp> selectArticleBykeyword(String accessToken,
			String keyword, Integer currentPage) {
		OauthToken oauthToken = null;
		if (accessToken != null) {
			oauthToken = oauthTokenMapper
					.getOauthTokenByAccessToken(accessToken);
		}

		String phone = null;
		if (oauthToken != null) {
			phone = oauthToken.getPhone();
		} else {
			phone = "88888888";
		}
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
		queryMap.put("phone", phone);
		queryMap.put("keyword", keyword);
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		return articleMapper.selectArticleByKeyword(queryMap);
	}
}
