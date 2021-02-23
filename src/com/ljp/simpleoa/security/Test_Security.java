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
 * �ӿڲ���+ SpringSecurity���û���¼ģ��
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
@Rollback(true) // �����Զ��ع���Ĭ����true�����Բ�д
public class Test_Security {

	private MockMvc mockMvc; // ģ��MVC����ͨ��MockMvcBuilders.webAppContextSetup(this.wac).build()��ʼ����

	@Autowired
	private SessionRegistrySerializable sessionRegistrySerializable;

	@Autowired
	private WebApplicationContext wac; // ע��WebApplicationContext

	@Before // �ڲ��Կ�ʼǰ��ʼ������
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}

	@Test
	//@WithUserDetails ģ���û��������û�����ͨ���Զ���UserDetails����֤
	//@WithAnonymousUser ģ�������û�
	//@WithMockUser ģ���û����ֶ�ָ���û�������Ȩ
	//@WithSecurityContext ͨ��SecurityContext������ģ���û�
	//@WithUserDetails(value = "10004", userDetailsServiceBeanName = "customUserDetailsService")
	@WithMockUser(username="10004",roles={"Ա��"})
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
				.andExpect(status().is(200))// ����״̬��
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// Ԥ�ڷ���ֵ��ý������text/plain;charset=UTF-8
				.andReturn();// ����ִ������Ľ��
		
//		Enumeration<String> attributeNames = result.getRequest().getAttributeNames();
//		while (attributeNames.hasMoreElements()) {
//			System.out.println(attributeNames.nextElement());
//		}
	}

	@Test
	public void testFormLoginSuccess() throws Exception {
		System.out.println("testFormLoginSuccess()");
		
		ArrayList<GrantedAuthority> arrayList = new ArrayList<GrantedAuthority>();
		Collections.addAll(arrayList, new SimpleGrantedAuthority("Ա��"));
		User user = new User("10004", "1234", arrayList);
		sessionRegistrySerializable.registerNewSession("21", user);
		
		// ���Ե�¼�ɹ�
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
		// ���Ե�¼ʧ��
		mockMvc.perform(formLogin("/login").user("admin").password("invalid")).andExpect(unauthenticated());
	}

	@Test
	public void testLogoutFail() throws Exception {
		System.out.println("testLogoutFail()");
		// �����˳���¼
		mockMvc.perform(logout("/logout")).andExpect(unauthenticated());
	}
}
