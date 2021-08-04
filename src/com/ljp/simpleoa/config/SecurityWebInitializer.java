package com.ljp.simpleoa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * 也可以用web.xml来配置
 * 把工作委托给springsecurityFilterChain来操作，大约有14个Filter
 * 1、WebAsyncManagerIntegrationFilter：
 * 		提供了对securityContext和WebAsyncManager的集成，其会把SecurityContext设置ThreadLocal中，
 * 		使其也能获取到用户上下文认证信息。
 * 2、SecurityContextPersistenceFilter
 * 		a.请求到来时，通过HttpSessionSecurityContextRepository接口从Session中读取SecurityContext，
 * 		如果读取结果为null，则创建之。获得SecurityContext之后，会将其存入SecurityContextHolder，
 * 		其中SecurityContextHolder默认是ThreadLocalSecurityContextHolderStrategy实例
 * 		b.请求结束时清空SecurityContextHolder，并将SecurityContext保存到Session中。
 * 3、HeaderWriterFilter
 * 		用来给http response添加一些Header，比如X-Frame-Options、X-XSS-Protection*、X-Content-Type-Options。
 * 		X-Frame-Options：防止某些重要网页被其他网站框架导入
 * 		X-XSS-Protection*：防止XSS攻击
 * 		X-Content-Security-Policy：这个响应头主要是用来定义页面可以加载哪些资源，减少XSS的发生
 * 		X-Content-Type-Options：互联网上的资源有各种类型，通常浏览器会根据响应头的Content-Type字段来分辨它们的类型。
 * 			例如：text/html、text/css
 * 4、CsrfFilter
 * 		防止csrf跨域攻击，伪造表单，要求表单POST提交时带有crsf令牌
 * 5、LogoutFilter
 * 		对登出url的请求处理，执行登出操作
 * 6、UsernamePasswordAuthenticationFilter
 * 		对登陆url的请求处理，校验用户账号密码，校验成功后执行以下操作：
 * 		a.(ConcurrentSessionStrategy)向框架特有的SessionRegistry添加用户和session信息，
 * 			并管理策略执行用户多点登陆检查，做出对应的‘不予许登陆’或‘令旧会话失效’和‘允许登陆的操作’
 * 		b.在SecurityContext存认证信息
 * 		c.执行rememberme服务，添加cookies
 * 		d.由eventpublisher发布认证事件
 * 		e.结束filter链，登陆成功跳转，执行新一轮的filter链检查（这次时已登陆状态不用再登陆啦）
 * 7、ConcurrentSessionFilter
 * 		根据当前sessionID检查SessionRegistry保存的session信息是否过期
 * 		过期：则执行登出操作（销毁旧过期session触发session事件从而删除SessionRegistry的信息）
 * 			跳转到login操作尝试自动登陆，此时一般会由rememberme执行登陆，此时登出操作一般会把其删除登陆失败，
 * 			跳转到登陆页，手动登陆
 * 		不过期：则刷新访问时间
 * 		不存在则跳过
 * 8、RequestCacheAwareFilter
 * 		将request存到session中，用于缓存request请求，可以用于恢复被登录而打断的请求
 * 		此处从session中取出request，存储request是在ExceptionTranslationFilter中
 * 9、SecurityContextHolderAwareRequestFilter
 * 		此过滤器对ServletRequest进行了一次包装，使得request具有更加丰富的API
 * 10、RememberMeAuthenticationFilter
 * 		服务器重启后rememberme失效，默认保存在内存的和SessionRegistry一样需要重新配置
 * 		读取客户端的remember的cookies来实现自动登陆，在SecurityContext存认证信息
 * 11、AnonymousAuthenticationFilter
 * 		前面都没有成功登陆则执行匿名登陆，在SecurityContext存认证信息
 * 12、SessionManagementFilter
 * 		1.session固化保护-通过session-fixation-protection配置 
 * 		2.session并发控制-通过concurrency-control配置 （这个主要在过过滤器6和7执行的）
 * 		和session相关的过滤器，内部维护了一个SessionAuthenticationStrategy，两者组合使用，
 * 		常用来防止session-fixation protection attack，以及限制同一用户开启多个会话的数量
 * 		与登录认证拦截时作用一样，持久化用户登录信息，可以保存到session中，也可以保存到cookie或者redis中。
 * 13、ExceptionTranslationFilter
 * 		异常拦截，其处在Filter链后部分，只能拦截其后面的节点并且只处理AuthenticationException
 * 		与AccessDeniedException两个异常。AuthenticationException指的是未登录状态下访问受保护资源，
 * 		AccessDeniedException指的是登陆了但是由于权限不足（比如普通用户访问管理员界面）。 
 * 14、FilterSecurityInterceptor
 * 		 这个filter用于授权验证。FilterSecurityInterceptor的工作流程：
 * 		FilterSecurityInterceptor从SecurityContextHolder中获取Authentication对象，
 * 		然后比对用户拥有的权限和资源所需的权限。前者可以通过Authentication对象直接获得，
 * 		而后者则需要引入我们之前一直未提到过的两个类：SecurityMetadataSource，AccessDecisionManager。
 * @author LiJunPeng
 *
 */
@Configuration
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer{
	public SecurityWebInitializer() {
		System.out.println("SecurityWebInitializer.constructor()");
	}

	/**
	 * 如果会话管理已指定最大会话数，则要开启
	 */
	@Override
	protected boolean enableHttpSessionEventPublisher() {
		System.out.println("SecurityWebInitializer.enableHttpSessionEventPublisher()");
		return true;
	}
}
