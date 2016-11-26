package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.ValidationCode;

public interface ValidationCodeMapper {

    int insert(ValidationCode record);

    int insertSelective(ValidationCode record);
    
    ValidationCode selectValidationCodeByPhone(String phone);
}