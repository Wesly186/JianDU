package com.mialab.jiandu.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mialab.jiandu.model.ResponseData;

public class ExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		CustomException customException = null;
		if (ex instanceof CustomException) {
			customException = (CustomException) ex;
		} else {
			customException = new CustomException(1000, "未知错误");
		}
		// 封装错误信息
		ResponseData<String> responseData = new ResponseData<String>(
				customException.getCode(), customException.getMessage(), null);
		Gson gson = new Gson();
		String jsonString = gson.toJson(responseData);
		response.setContentType("application/json;charset=utf-8");
		try {
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView();
	}

}
