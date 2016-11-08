package com.mialab.jiandu.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.mapper.UserMapper;
import com.mialab.jiandu.mapper.ValidationCodeMapper;
import com.mialab.jiandu.model.User;
import com.mialab.jiandu.model.ValidationCode;
import com.mialab.jiandu.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ValidationCodeMapper validationCodeMapper;

	@Override
	public void register(User user) {
		ValidationCode validationCode = new ValidationCode();
		validationCode.setExpires(new Date());
		validationCode.setGenerateTime(new Date());
		validationCode.setNextTime(new Date());
		validationCode.setPhone("18625210821");
		validationCode.setValidationCode(1234);
		
		validationCodeMapper.insert(validationCode);
		userMapper.insert(user);
	}
}
