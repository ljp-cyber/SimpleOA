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
@EnableWebSecurity//spring4后@EnableWebMvcSecurity弃用，@EnableWebSecurity自动添加Mvc支持
@ComponentScan(basePackageClasses=MyRoleVoter.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SessionRegistry sessionRegistry;

	// 地址URL，配置里的url相对项目下的，httprequest获取的路径相对服务器里的
	private static final String loginUrl = "/login";
	private static final String loginUrl_long = "/SimpleOA/login";
	// private static final String logoutUrl = "/logout";
	private static final String logoutUrl_long = "/SimpleOA/logout";
	private static final String indexUrl = "/index";
	// private static final String indexUrl_long="/SimpleOA/index";
	// private static final String loginFailUrl="/login.jsp?err";
	// private static final String loginFailUrl_long="/SimpleOA/login.jsp?err";

	// 允许最大访问数量
	private static final int maximumSessions = 1;

	private ComboPooledDataSource dataSource;
	//@Autowired
//	private MyRoleVoter myRoleVoter;

	
	public SecurityConfig(ComboPooledDataSource dataSource) {
		System.out.println("SecurityConfig.constructor()");
		this.dataSource=dataSource;
	}

	/**
	 * 授权拦截器默认为AffirmativeBased，一票通过
	 * 由默认的RoleVoter和AuthenticatedVoter，还有WebExpressionVoter
	 * @return
	 */
	public AccessDecisionManager getAccessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> list = new ArrayList<>();//投票器表
		list.add(new WebExpressionVoter());//用过spel表达时处理，例如：antMatchers(loginUrl).permitAll()
		list.add(new RoleVoter());
//		if(myRoleVoter==null) {
//			System.out.println("myRoleVoter注入失败");
//		}
		//list.add(myRoleVoter);
		list.add(new AuthenticatedVoter());
		return new UnanimousBased(list);//全部通过才通过
		//return new ConsensusBased();//少数服从多数
		//return new AffirmativeBased();//一票以上则通过
	}

	@Bean
	public SessionRegistry getSessionRegistry() {
		SessionRegistrySerializable sessionRegistry;
		sessionRegistry = IOUtils.loadObj(SessionRegistrySerializable.fileUrl);
		if (sessionRegistry == null) {
			System.out.println("会话注册表加载为空");
			return new SessionRegistrySerializable();
		}
		sessionRegistry.loadAllSessionInformations();
		System.out.println("会话注册表加载成功");
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
		// 默认的设置是：
		// http.authorizeRequests()//用户登陆过滤
		// .anyRequest()//所有路径都过滤，另外可以通过antMatchers或者regexMatchers方法匹配路径
		// .authenticated()//允许认证的用户访问
		// .and()//返回HttpSecurity进行配置连接
		// .formLogin()//启用默认登陆页面
		// .and()
		// .httpBasic()//启用httpBasic认证，对话框认证？
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
		http.authorizeRequests()// 用户登陆过滤
				.accessDecisionManager(getAccessDecisionManager())
				.antMatchers(loginUrl).permitAll()//login不需认证，还可以regexMatchers方法匹配路径
				.anyRequest().authenticated()// 其他路径允许认证的用户访问
				.and()// 返回HttpSecurity进行配置连接
				// --------------登陆设置------------
				.formLogin()// 启用默认登陆页面
				.loginPage(loginUrl).defaultSuccessUrl(indexUrl, true).usernameParameter("workerSn")
				.passwordParameter("workerPw").failureHandler(new AuthenticationFailureHandler() {
					//重写页面使得可以显示登陆失败信息
					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {
						// request.setAttribute("loginInfo", "账号或密码错误！");
						request.getSession().setAttribute("loginInfo", "账号或密码错误");
						response.sendRedirect(loginUrl_long + "?errer");
					}
				}).and()
				// --------------记住我设置-----------
				.rememberMe()//默认存在内存，要更改配置，要不服务器重启失效
				.and()
				// --------------登出设置------------
				.logout()
				// .logoutUrl("/signOut")//自定义退出的地址，只能接受post请求
				.logoutRequestMatcher(new RequestMatcher() {// 退出请求路径自定义匹配
					@Override
					public boolean matches(HttpServletRequest arg0) {
						String requestURI = arg0.getRequestURI();
						System.out.println(requestURI);
						if (requestURI.equals(logoutUrl_long)) {
							return true;
						} else
							return false;
					}
				}).logoutSuccessUrl(loginUrl)// 退出之后跳转到注册页面
				.invalidateHttpSession(true) // 指定是否在注销时让httpSession无效
				.deleteCookies("JSESSIONID")// 删除当前的JSESSIONID,这个是会话ID
				.deleteCookies("remember-me").and()
				// --------------会话和用户登陆管理设置------------
				.sessionManagement().invalidSessionUrl(indexUrl)// session失效跳转
				.maximumSessions(maximumSessions).expiredUrl(loginUrl).sessionRegistry(sessionRegistry)
				.maxSessionsPreventsLogin(false)// 同一个用户在其他地方登录将前一个剔除下线或不能登陆
		;
		// 关闭csrf过滤器，跨域攻击，统一ip

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
