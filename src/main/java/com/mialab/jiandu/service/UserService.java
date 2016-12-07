package com.mialab.jiandu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mialab.jiandu.model.Rank;
import com.mialab.jiandu.model.User;
import com.mialab.jiandu.model.UserRsp;

public interface UserService {

	void register(User user, String validationCode) throws Exception;

	void getValidationCode(String phone, String business) throws Exception;

	User updateProfile(String accessToken, MultipartFile headPic, User user)
			throws Exception;

	void updatePassword(String phone, String oldPassword, String newPassword)
			throws Exception;

	UserRsp getUserInfoByPhone(String phone) throws Exception;

	List<Rank> getRankByReads();
	
	List<Rank> getRankByContributions();
}
