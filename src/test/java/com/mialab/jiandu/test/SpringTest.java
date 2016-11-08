package com.mialab.jiandu.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mialab.jiandu.model.User;
import com.mialab.jiandu.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:conf/spring/applicationContext-service.xml",
		"classpath:conf/spring/applicationContext-dao.xml",
		"classpath:conf/spring/applicationContext-transation.xml" })
public class SpringTest {

	@Autowired
	private UserService userService;

	@Test
	public void demo1() throws Exception {
		User user = new User();
		user.setPhone("18625210821");
		user.setPassword("123456");
		user.setUsername("wesly186");
		user.setSalt("fdgdfg");
		user.setRegisterTime(new Date());
		userService.register(user);
	}
}