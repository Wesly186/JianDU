package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.ValidationCodeCustom;

public interface ValidationCodeCustomMapper {
	
    ValidationCodeCustom selectValidationCodeByPhone(String phone);
}