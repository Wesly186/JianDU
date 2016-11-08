package com.mialab.jiandu.service;

import com.mialab.jiandu.model.User;

public interface UserService {

	void register(User user) throws Exception;

	void getValidateCode(String phone, String business) throws Exception;
}
