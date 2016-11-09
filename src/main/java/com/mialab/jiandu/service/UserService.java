package com.mialab.jiandu.service;

import com.mialab.jiandu.model.User;

public interface UserService {

	User register(User user, String validationCode) throws Exception;

	void getValidationCode(String phone, String business) throws Exception;
}
