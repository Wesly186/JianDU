package com.mialab.jiandu.service;

import java.util.List;

import com.mialab.jiandu.model.Activity;
import com.mialab.jiandu.model.Message;

public interface NotifyService {

	public List<Message> getMessages(String accessToken, int currentPage)
			throws Exception;

	public List<Activity> getActivities(String accessToken, int currentPage)
			throws Exception;
}
