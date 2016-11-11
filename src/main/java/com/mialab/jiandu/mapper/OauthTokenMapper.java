package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.OauthToken;
import com.mialab.jiandu.model.OauthTokenExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OauthTokenMapper {
    int countByExample(OauthTokenExample example);

    int deleteByExample(OauthTokenExample example);

    int deleteByPrimaryKey(String phone);

    int insert(OauthToken record);

    int insertSelective(OauthToken record);

    List<OauthToken> selectByExample(OauthTokenExample example);

    OauthToken selectByPrimaryKey(String phone);

    int updateByExampleSelective(@Param("record") OauthToken record, @Param("example") OauthTokenExample example);

    int updateByExample(@Param("record") OauthToken record, @Param("example") OauthTokenExample example);

    int updateByPrimaryKeySelective(OauthToken record);

    int updateByPrimaryKey(OauthToken record);
    
    OauthToken getOauthTokenByAccessToken(String accessToken);

	OauthToken selectByRefreshToken(String refreshToken);
}