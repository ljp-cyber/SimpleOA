package com.ljp.simpleoa.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.ljp.simpleoa.config.RootConfig;
import com.ljp.simpleoa.config.SecurityConfig;
import com.ljp.simpleoa.config.SecurityWebInitializer;
import com.ljp.simpleoa.config.WebAppInitializer;
import com.ljp.simpleoa.config.WebConfig;
import com.ljp.simpleoa.model.Receipts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 接口测试+ SpringSecurity的用户登录模拟
 */
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
//		WebAppInitializer.class,
//		SecurityWebInitializer.class, 
		RootConfig.class, 
		WebConfig.class, 
		SecurityConfig.class 
		})
@WebAppConfiguration
@Transactional
@Rollback(true) // 事务自动回滚，默认是true。可以不写
public class Test_Security {

	private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

	@Autowired
	private SessionRegistrySerializable sessionRegistrySerializable;

	@Autowired
	private WebApplicationContext wac; // 注入WebApplicationContext

	@Before // 在测试开始前初始化工作
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}

	@Test
	//@WithUserDetails 模拟用户，给定用户名，通过自定义UserDetails来认证
	//@WithAnonymousUser 模拟匿名用户
	//@WithMockUser 模拟用户，手动指定用户名和授权
	//@WithSecurityContext 通过SecurityContext构造器模拟用户
	//@WithUserDetails(value = "10004", userDetailsServiceBeanName = "customUserDetailsService")
	@WithMockUser(username="10004",roles={"员工"})
	public void testQ1() throws Exception {
		System.out.println("testQ1()");
		
		Map<String, Object> map = new HashMap<>();
		map.put("receiptsId", "37");
		MockHttpSession mockHttpSession=new MockHttpSession();
		
		MvcResult result1 = mockMvc
				.perform(MockMvcRequestBuilders.get("/receipts/toAdd")
						.session(mockHttpSession)
						)
				.andExpect(status().is(200))
				.andReturn()
				;
		CsrfToken _csrf = (CsrfToken)result1.getRequest().getAttribute("_csrf");
		System.out.println("_csrf:"+_csrf.getToken());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/receipts/detail")
//						.contentType(MediaType.APPLICATION_JSON_UTF8)
//						.content(JSONObject.toJSONString(map))
						.session(mockHttpSession)
						.param("receiptsId", "37")
						.param("_csrf", _csrf.getToken())
						)
				.andExpect(status().is(200))// 期望状态码
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果
		
//		Enumeration<String> attributeNames = result.getRequest().getAttributeNames();
//		while (attributeNames.hasMoreElements()) {
//			System.out.println(attributeNames.nextElement());
//		}
	}

	@Test
	public void testFormLoginSuccess() throws Exception {
		System.out.println("testFormLoginSuccess()");
		
		ArrayList<GrantedAuthority> arrayList = new ArrayList<GrantedAuthority>();
		Collections.addAll(arrayList, new SimpleGrantedAuthority("员工"));
		User user = new User("10004", "1234", arrayList);
		sessionRegistrySerializable.registerNewSession("21", user);
		
		// 测试登录成功
		FormLoginRequestBuilder formLoginRequestBuilder = formLogin("/login")
				.user("workerSn", "10004")
				.password("workerPw", "1234")
				;
		mockMvc
		.perform(formLoginRequestBuilder)
		.andExpect(authenticated())
		;
		mockMvc
		.perform(formLoginRequestBuilder)
//		.andExpect(authenticated())
		;
		sessionRegistrySerializable.show();
	}

	@Test
	public void testFormLoginFail() throws Exception {
		System.out.println("testFormLoginFail()");
		// 测试登录失败
		mockMvc.perform(formLogin("/login").user("admin").password("invalid")).andExpect(unauthenticated());
	}

	@Test
	public void testLogoutFail() throws Exception {
		System.out.println("testLogoutFail()");
		// 测试退出登录
		mockMvc.perform(logout("/logout")).andExpect(unauthenticated());
	}
}
