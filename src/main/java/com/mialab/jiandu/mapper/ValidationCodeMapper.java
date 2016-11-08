package com.mialab.jiandu.mapper;

import com.mialab.jiandu.model.ValidationCode;
import com.mialab.jiandu.model.ValidationCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ValidationCodeMapper {
    int countByExample(ValidationCodeExample example);

    int deleteByExample(ValidationCodeExample example);

    int insert(ValidationCode record);

    int insertSelective(ValidationCode record);

    List<ValidationCode> selectByExample(ValidationCodeExample example);

    int updateByExampleSelective(@Param("record") ValidationCode record, @Param("example") ValidationCodeExample example);

    int updateByExample(@Param("record") ValidationCode record, @Param("example") ValidationCodeExample example);
}