package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.AppVersion;

public interface AppVersionMapper {

    int insert(AppVersion record);

    int insertSelective(AppVersion record);
    
    AppVersion findByVersionCode(int versionCode);
}