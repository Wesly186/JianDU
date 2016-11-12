package com.mialab.jiandu.service;

import org.springframework.web.multipart.MultipartFile;

import com.mialab.jiandu.model.User;

public interface UserService {

	void register(User user, String validationCode) throws Exception;

	void getValidationCode(String phone, String business) throws Exception;

	User updateProfile(String accessToken, MultipartFile headPic, User user)
			throws Exception;
}
