package com.mialab.jiandu.web.interceptor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.model.OauthTokenCustom;
import com.mialab.jiandu.service.OauthTokenService;
import com.mialab.jiandu.utils.ResourcesUtil;

public class AuthenticateInterceptor implements HandlerInterceptor {

	private List<String> anonURIList;
	@Autowired
	private OauthTokenService oauthTokenService;

	/**
	 * controller执行前调用此方法,进行非匿名url拦截，检验access_token
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		if (anonURIList == null) {
			anonURIList = ResourcesUtil.getkeyList("conf/AnonUrl");
		}
		// 允许匿名访问的地址
		for (String url : anonURIList) {
			if (requestURI.indexOf(url) >= 0) {
				return true;
			}
		}
		// 没有带上accessToken信息的拦截
		String accessToken = request.getParameter("accessToken");
		if (accessToken == null) {
			throw new CustomException(401, "禁止匿名访问");
		}
		// accessToken过期的拦截
		OauthTokenCustom oauthTokenCustom = oauthTokenService
				.getOauthTokenByAccessToken(accessToken);
		if (oauthTokenCustom == null) {
			throw new CustomException(402, "无效的AccessToken");
		} else if (oauthTokenCustom.getAccessTokenExpires().getTime() < new Date()
				.getTime()) {
			throw new CustomException(402, "无效的AccessToken");
		} else {
			return true;
		}
	}

	/**
	 * controller执行后（返回ModelAndView）但未返回视图前调用此方法
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
