package com.mialab.jiandu.serviceImpl;

import java.util.Date;

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
	public void register(User user) {

	}

	@Override
	public void getValidateCode(String phone, String business)
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
		req.setSmsType("normal");
		req.setRecNum(phone);
		if (business.equals(GlobalConf.BUSINESS_REGISTER)) {
			req.setSmsFreeSignName("注册验证");
			req.setSmsTemplateCode("SMS_12195568");
			req.setSmsParamString("{\"code\":\"" + code
					+ "\",\"product\":\"简读App\"}");
		} else {
			req.setSmsFreeSignName("变更验证");
			req.setSmsTemplateCode("SMS_12195566");
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
