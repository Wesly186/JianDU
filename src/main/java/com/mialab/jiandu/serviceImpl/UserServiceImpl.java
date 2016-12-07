package com.mialab.jiandu.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mialab.jiandu.conf.GlobalConf;
import com.mialab.jiandu.exception.CustomException;
import com.mialab.jiandu.mapper.OauthTokenMapper;
import com.mialab.jiandu.mapper.UserMapper;
import com.mialab.jiandu.mapper.ValidationCodeMapper;
import com.mialab.jiandu.model.OauthToken;
import com.mialab.jiandu.model.Rank;
import com.mialab.jiandu.model.User;
import com.mialab.jiandu.model.UserRsp;
import com.mialab.jiandu.model.ValidationCode;
import com.mialab.jiandu.service.UserService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ValidationCodeMapper validationCodeMapper;
	@Autowired
	private OauthTokenMapper oauthTokenMapper;

	@Override
	public void register(User user, String validationCode)
			throws CustomException, OAuthSystemException {
		ValidationCode validationCodeCustom = validationCodeMapper
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
		user.setSex("男");
		user.setRegisterTime(new Date());
		userMapper.insertSelective(user);
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
		ValidationCode checkCode = validationCodeMapper
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

	@Override
	public User updateProfile(String accessToken, MultipartFile headPic,
			User user) throws IllegalStateException, IOException {
		String newFileName = null;
		if (headPic != null) {
			// 原始文件名称
			String pictureFile_name = headPic.getOriginalFilename();
			// 新文件名称
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(new Date());
			newFileName = dateString
					+ "/"
					+ UUID.randomUUID().toString()
					+ pictureFile_name.substring(pictureFile_name
							.lastIndexOf("."));
			// 上传图片
			File uploadPic = new File(
					"/usr/tomcat/apache-tomcat-8.0.39/webapps/pic/"
							+ newFileName);
			if (!uploadPic.exists()) {
				uploadPic.mkdirs();
			}
			// 向磁盘写文件
			headPic.transferTo(uploadPic);
		}

		OauthToken oauthToken = oauthTokenMapper
				.getOauthTokenByAccessToken(accessToken);
		user.setPhone(oauthToken.getPhone());
		if (newFileName != null) {
			user.setHeadPic(newFileName);
		}
		userMapper.updateByPrimaryKeySelective(user);
		user = userMapper.selectByPrimaryKey(user.getPhone());

		return user;
	}

	@Override
	public void updatePassword(String phone, String oldPassword,
			String newPassword) throws Exception {
		User user = userMapper.selectByPrimaryKey(phone);
		// 密码加密
		MD5Generator generator = new MD5Generator();
		String generatePassword = generator.generateValue(oldPassword);
		if (!user.getPassword().equals(generatePassword)) {
			throw new CustomException(412, "旧密码不正确！");
		} else {
			String newPass = generator.generateValue(newPassword);
			user.setPassword(newPass);
			userMapper.updateByPrimaryKeySelective(user);
		}
	}

	@Override
	public UserRsp getUserInfoByPhone(String phone) throws Exception {
		return userMapper.selectLoginInfoByPrimaryKey(phone);
	}

	@Override
	public List<Rank> getRankByReads() {
		List<Rank> ranks = userMapper.selectByReads();
		for (int i = 0; i < ranks.size(); i++) {
			ranks.get(i).setRankNum(i + 1);
		}
		return ranks;
	}

	@Override
	public List<Rank> getRankByContributions() {
		List<Rank> ranks = userMapper.selectByContributions();
		for (int i = 0; i < ranks.size(); i++) {
			ranks.get(i).setRankNum(i + 1);
		}
		return ranks;
	}
}
