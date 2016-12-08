package com.mialab.jiandu.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.conf.GlobalConf;
import com.mialab.jiandu.mapper.NotifyMapper;
import com.mialab.jiandu.mapper.OauthTokenMapper;
import com.mialab.jiandu.mapper.UserMapper;
import com.mialab.jiandu.model.Activity;
import com.mialab.jiandu.model.Message;
import com.mialab.jiandu.model.OauthToken;
import com.mialab.jiandu.model.User;
import com.mialab.jiandu.service.NotifyService;

@Service
public class NotifyServiceImpl implements NotifyService {

	@Autowired
	private NotifyMapper notifyMapper;
	@Autowired
	private OauthTokenMapper oauthTokenMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Message> getMessages(String accessToken, int currentPage)
			throws Exception {
		OauthToken oauthToken = oauthTokenMapper
				.getOauthTokenByAccessToken(accessToken);

		User writer = userMapper.selectByPrimaryKey(oauthToken.getPhone());

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("phone", oauthToken.getPhone());
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);

		List<Message> messages = notifyMapper.getMessages(queryMap);
		for (Message message : messages) {
			message.setWriter(writer);
		}
		return notifyMapper.getMessages(queryMap);
	}

	@Override
	public List<Activity> getActivities(String accessToken, int currentPage)
			throws Exception {
		OauthToken oauthToken = oauthTokenMapper
				.getOauthTokenByAccessToken(accessToken);

		Map<String, Object> queryMap = new HashMap<String, Object>();

		queryMap.put("phone", oauthToken.getPhone());
		queryMap.put("limit", GlobalConf.PAGE_SIZE);
		queryMap.put("offset", currentPage * GlobalConf.PAGE_SIZE);
		// return notifyMapper.getActivities(queryMap);
		return null;
	}
}
