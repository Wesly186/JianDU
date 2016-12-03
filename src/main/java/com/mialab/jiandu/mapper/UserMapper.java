package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.User;
import com.mialab.jiandu.model.UserRsp;

public interface UserMapper {

    int deleteByPrimaryKey(String phone);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String phone);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	UserRsp selectLoginInfoByPrimaryKey(String phone);
}