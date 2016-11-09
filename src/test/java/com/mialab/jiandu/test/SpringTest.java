package com.mialab.jiandu.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
		System.out.println("18625210821".substring(7));
	}
}