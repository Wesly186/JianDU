package com.mialab.jiandu.mapper;

import java.util.List;
import java.util.Map;

import com.mialab.jiandu.model.Activity;
import com.mialab.jiandu.model.Message;

public interface NotifyMapper {
	public List<Message> getMessages(Map<String, Object> queryMap)
			throws Exception;

	/*public List<Activity> getActivities(Map<String, Object> queryMap)
			throws Exception;*/
}