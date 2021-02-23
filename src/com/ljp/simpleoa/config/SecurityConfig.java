package com.ljp.simpleoa.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.ljp.simpleoa.security.MyRoleVoter;
import com.ljp.simpleoa.security.SessionRegistrySerializable;
import com.ljp.simpleoa.utils.IOUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebSecurity//spring4��@EnableWebMvcSecurity���ã�@EnableWebSecurity�Զ����Mvc֧��
@ComponentScan(basePackageClasses=MyRoleVoter.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SessionRegistry sessionRegistry;

	// ��ַURL���������url�����Ŀ�µģ�httprequest��ȡ��·����Է��������
	private static final String loginUrl = "/login";
	private static final String loginUrl_long = "/SimpleOA/login";
	// private static final String logoutUrl = "/logout";
	private static final String logoutUrl_long = "/SimpleOA/logout";
	private static final String indexUrl = "/index";
	// private static final String indexUrl_long="/SimpleOA/index";
	// private static final String loginFailUrl="/login.jsp?err";
	// private static final String loginFailUrl_long="/SimpleOA/login.jsp?err";

	// ��������������
	private static final int maximumSessions = 1;

	private ComboPooledDataSource dataSource;
	//@Autowired
//	private MyRoleVoter myRoleVoter;

	
	public SecurityConfig(ComboPooledDataSource dataSource) {
		System.out.println("SecurityConfig.constructor()");
		this.dataSource=dataSource;
	}

	/**
	 * ��Ȩ������Ĭ��ΪAffirmativeBased��һƱͨ��
	 * ��Ĭ�ϵ�RoleVoter��AuthenticatedVoter������WebExpressionVoter
	 * @return
	 */
	public AccessDecisionManager getAccessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> list = new ArrayList<>();//ͶƱ����
		list.add(new WebExpressionVoter());//�ù�spel���ʱ�������磺antMatchers(loginUrl).permitAll()
		list.add(new RoleVoter());
//		if(myRoleVoter==null) {
//			System.out.println("myRoleVoterע��ʧ��");
//		}
		//list.add(myRoleVoter);
		list.add(new AuthenticatedVoter());
		return new UnanimousBased(list);//ȫ��ͨ����ͨ��
		//return new ConsensusBased();//�������Ӷ���
		//return new AffirmativeBased();//һƱ������ͨ��
	}

	@Bean
	public SessionRegistry getSessionRegistry() {
		SessionRegistrySerializable sessionRegistry;
		sessionRegistry = IOUtils.loadObj(SessionRegistrySerializable.fileUrl);
		if (sessionRegistry == null) {
			System.out.println("�Ựע������Ϊ��");
			return new SessionRegistrySerializable();
		}
		sessionRegistry.loadAllSessionInformations();
		System.out.println("�Ựע�����سɹ�");
		return sessionRegistry;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("SecurityConfig.AuthenticationManagerBuilder()");
		// auth.inMemoryAuthentication()
		// .withUser("admin").password("1234").roles("ADMIN")
		// .and()
		// .withUser("admin1").password("1234").roles("USER")
		// ;
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select worker_sn,worker_pw,true from worker where worker_sn = ?")
				.authoritiesByUsernameQuery("select worker_sn,post from worker where worker_sn = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		// Ĭ�ϵ������ǣ�
		// http.authorizeRequests()//�û���½����
		// .anyRequest()//����·�������ˣ��������ͨ��antMatchers����regexMatchers����ƥ��·��
		// .authenticated()//������֤���û�����
		// .and()//����HttpSecurity������������
		// .formLogin()//����Ĭ�ϵ�½ҳ��
		// .and()
		// .httpBasic()//����httpBasic��֤���Ի�����֤��
		// ;
		System.out.println("SecurityConfig.HttpSecurity()");
		http.addFilterBefore(new Filter() {

			@Override
			public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
					throws IOException, ServletException {
				HttpServletRequest httpServletRequest = (HttpServletRequest) arg0;
				HttpServletResponse httpServletResponse = (HttpServletResponse) arg1;
				httpServletRequest.setCharacterEncoding("UTF-8");
				httpServletResponse.setCharacterEncoding("UTF-8");

				arg2.doFilter(httpServletRequest, httpServletResponse);
			}
		}, WebAsyncManagerIntegrationFilter.class);
		http.authorizeRequests()// �û���½����
				.accessDecisionManager(getAccessDecisionManager())
				.antMatchers(loginUrl).permitAll()//login������֤��������regexMatchers����ƥ��·��
				.anyRequest().authenticated()// ����·��������֤���û�����
				.and()// ����HttpSecurity������������
				// --------------��½����------------
				.formLogin()// ����Ĭ�ϵ�½ҳ��
				.loginPage(loginUrl).defaultSuccessUrl(indexUrl, true).usernameParameter("workerSn")
				.passwordParameter("workerPw").failureHandler(new AuthenticationFailureHandler() {
					//��дҳ��ʹ�ÿ�����ʾ��½ʧ����Ϣ
					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {
						// request.setAttribute("loginInfo", "�˺Ż��������");
						request.getSession().setAttribute("loginInfo", "�˺Ż��������");
						response.sendRedirect(loginUrl_long + "?errer");
					}
				}).and()
				// --------------��ס������-----------
				.rememberMe()//Ĭ�ϴ����ڴ棬Ҫ�������ã�Ҫ������������ʧЧ
				.and()
				// --------------�ǳ�����------------
				.logout()
				// .logoutUrl("/signOut")//�Զ����˳��ĵ�ַ��ֻ�ܽ���post����
				.logoutRequestMatcher(new RequestMatcher() {// �˳�����·���Զ���ƥ��
					@Override
					public boolean matches(HttpServletRequest arg0) {
						String requestURI = arg0.getRequestURI();
						System.out.println(requestURI);
						if (requestURI.equals(logoutUrl_long)) {
							return true;
						} else
							return false;
					}
				}).logoutSuccessUrl(loginUrl)// �˳�֮����ת��ע��ҳ��
				.invalidateHttpSession(true) // ָ���Ƿ���ע��ʱ��httpSession��Ч
				.deleteCookies("JSESSIONID")// ɾ����ǰ��JSESSIONID,����ǻỰID
				.deleteCookies("remember-me").and()
				// --------------�Ự���û���½��������------------
				.sessionManagement().invalidSessionUrl(indexUrl)// sessionʧЧ��ת
				.maximumSessions(maximumSessions).expiredUrl(loginUrl).sessionRegistry(sessionRegistry)
				.maxSessionsPreventsLogin(false)// ͬһ���û��������ط���¼��ǰһ���޳����߻��ܵ�½
		;
		// �ر�csrf�����������򹥻���ͳһip

		// http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(web);
		System.out.println("SecurityConfig.WebSecurity()");
		web.ignoring().antMatchers("/assets/**");
		web.ignoring().antMatchers("/vendor/**");
		web.ignoring().antMatchers("/js/**");
	}

}
