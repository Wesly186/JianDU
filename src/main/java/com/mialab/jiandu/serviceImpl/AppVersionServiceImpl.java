package com.mialab.jiandu.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.mapper.AppVersionMapper;
import com.mialab.jiandu.model.AppVersion;
import com.mialab.jiandu.service.AppVersionService;

@Service
public class AppVersionServiceImpl implements AppVersionService {

	@Autowired
	private AppVersionMapper appVersionMapper;

	@Override
	public AppVersion getLatestVersion(int versionCode) {
		return appVersionMapper.findByVersionCode(versionCode);
	}

}
