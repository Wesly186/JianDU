package com.mialab.jiandu.serviceImpl;

import java.util.Date;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.mapper.UserMapper;
import com.mialab.jiandu.mapper.ValidationCodeCustomMapper;
import com.mialab.jiandu.mapper.ValidationCodeMapper;
import com.mialab.jiandu.model.User;
import com.mialab.jiandu.model.ValidationCode;
import com.mialab.jiandu.model.ValidationCodeCustom;
import com.mialab.jiandu.service.UserService;
import com.mialab.jiandu.utils.GlobalConf;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ValidationCodeCustomMapper validationCodeCustomMapper;
	@Autowired
	private ValidationCodeMapper validationCodeMapper;

	@Override
	public User register(User user, String validationCode)
			throws CustomException, OAuthSystemException {
		ValidationCodeCustom validationCodeCustom = validationCodeCustomMapper
				.selectValidationCodeByPhone(user.getPhone());
		// 非法验证码
		if (validationCodeCustom == null) {
			throw new CustomException(408, "非法验证码");
		}
		// 验证码过期
		if (validationCodeCustom.getExpires().getTime() < new Date().getTime()) {
			throw new CustomException(407, "验证码过期");
		}
		// 该用户已经注册
		if (userMapper.selectByPrimaryKey(user.getPhone()) != null) {
			throw new CustomException(409, "该手机号已经注册");
		}
		// 密码加密
		MD5Generator generator = new MD5Generator();
		String generatePassword = generator.generateValue(user.getPassword());
		user.setPassword(generatePassword);
		user.setUsername("简读" + user.getPhone().substring(7));
		user.setRegisterTime(new Date());
		userMapper.insert(user);
		// 回显数据不应包含密码
		user.setPassword(null);
		return user;
	}

	@Override
	public void getValidationCode(String phone, String business)
			throws CustomException {
		if (business.equals(GlobalConf.BUSINESS_REGISTER)) {
			User appUser = userMapper.selectByPrimaryKey(phone);
			if (appUser != null) {
				throw new CustomException(405, "该手机号已经被注册");
			}
		}
		ValidationCodeCustom checkCode = validationCodeCustomMapper
				.selectValidationCodeByPhone(phone);
		int code = (int) (Math.random() * 9000 + 1000);
		// 阻止重复频繁发送
		if (checkCode != null
				&& checkCode.getNextTime().getTime() > new Date().getTime()) {
			throw new CustomException(406, "获取验证码频率过高");
		}
		validationCodeMapper.insert(new ValidationCode(phone, code, new Date(
				new Date().getTime() + 1000 * 60 * 10), new Date(new Date()
				.getTime() + 1000 * 60), new Date()));

		TaobaoClient client = new DefaultTaobaoClient(GlobalConf.DAYU_URL,
				GlobalConf.DAYU_KEY, GlobalConf.DAYU_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsFreeSignName("简读社区");
		req.setSmsType("normal");
		req.setRecNum(phone);
		if (business.equals(GlobalConf.BUSINESS_REGISTER)) {
			req.setSmsTemplateCode("SMS_25685251");
			req.setSmsParamString("{\"code\":\"" + code
					+ "\",\"product\":\"简读App\"}");
		} else {
			req.setSmsTemplateCode("SMS_25615153");
			req.setSmsParamString("{\"code\":\"" + code
					+ "\",\"product\":\"简读App\"}");
		}
		try {
			client.execute(req);
		} catch (ApiException e) {
			throw new CustomException(404, "短信服务器出错");
		}
	}
}
