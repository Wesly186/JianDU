package com.mialab.jiandu.service;

import com.mialab.jiandu.model.AppVersion;

public interface AppVersionService {
	AppVersion getLatestVersion(int versionCode);
}
