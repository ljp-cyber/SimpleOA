package com.ljp.simpleoa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Ҳ������web.xml������
 * �ѹ���ί�и�springsecurityFilterChain����������Լ��14��Filter
 * 1��WebAsyncManagerIntegrationFilter��
 * 		�ṩ�˶�securityContext��WebAsyncManager�ļ��ɣ�����SecurityContext����ThreadLocal�У�
 * 		ʹ��Ҳ�ܻ�ȡ���û���������֤��Ϣ��
 * 2��SecurityContextPersistenceFilter
 * 		a.������ʱ��ͨ��HttpSessionSecurityContextRepository�ӿڴ�Session�ж�ȡSecurityContext��
 * 		�����ȡ���Ϊnull���򴴽�֮�����SecurityContext֮�󣬻Ὣ�����SecurityContextHolder��
 * 		����SecurityContextHolderĬ����ThreadLocalSecurityContextHolderStrategyʵ��
 * 		b.�������ʱ���SecurityContextHolder������SecurityContext���浽Session�С�
 * 3��HeaderWriterFilter
 * 		������http response���һЩHeader������X-Frame-Options��X-XSS-Protection*��X-Content-Type-Options��
 * 		X-Frame-Options����ֹĳЩ��Ҫ��ҳ��������վ��ܵ���
 * 		X-XSS-Protection*����ֹXSS����
 * 		X-Content-Security-Policy�������Ӧͷ��Ҫ����������ҳ����Լ�����Щ��Դ������XSS�ķ���
 * 		X-Content-Type-Options���������ϵ���Դ�и������ͣ�ͨ��������������Ӧͷ��Content-Type�ֶ����ֱ����ǵ����͡�
 * 			���磺text/html��text/css
 * 4��CsrfFilter
 * 		��ֹcsrf���򹥻���α�����Ҫ���POST�ύʱ����crsf����
 * 5��LogoutFilter
 * 		�Եǳ�url��������ִ�еǳ�����
 * 6��UsernamePasswordAuthenticationFilter
 * 		�Ե�½url��������У���û��˺����룬У��ɹ���ִ�����²�����
 * 		a.(ConcurrentSessionStrategy)�������е�SessionRegistry����û���session��Ϣ��
 * 			���������ִ���û�����½��飬������Ӧ�ġ��������½������ɻỰʧЧ���͡������½�Ĳ�����
 * 		b.��SecurityContext����֤��Ϣ
 * 		c.ִ��rememberme�������cookies
 * 		d.��eventpublisher������֤�¼�
 * 		e.����filter������½�ɹ���ת��ִ����һ�ֵ�filter����飨���ʱ�ѵ�½״̬�����ٵ�½����
 * 7��ConcurrentSessionFilter
 * 		���ݵ�ǰsessionID���SessionRegistry�����session��Ϣ�Ƿ����
 * 		���ڣ���ִ�еǳ����������پɹ���session����session�¼��Ӷ�ɾ��SessionRegistry����Ϣ��
 * 			��ת��login���������Զ���½����ʱһ�����remembermeִ�е�½����ʱ�ǳ�����һ������ɾ����½ʧ�ܣ�
 * 			��ת����½ҳ���ֶ���½
 * 		�����ڣ���ˢ�·���ʱ��
 * 		������������
 * 8��RequestCacheAwareFilter
 * 		��request�浽session�У����ڻ���request���󣬿������ڻָ�����¼����ϵ�����
 * 		�˴���session��ȡ��request���洢request����ExceptionTranslationFilter��
 * 9��SecurityContextHolderAwareRequestFilter
 * 		�˹�������ServletRequest������һ�ΰ�װ��ʹ��request���и��ӷḻ��API
 * 10��RememberMeAuthenticationFilter
 * 		������������remembermeʧЧ��Ĭ�ϱ������ڴ�ĺ�SessionRegistryһ����Ҫ��������
 * 		��ȡ�ͻ��˵�remember��cookies��ʵ���Զ���½����SecurityContext����֤��Ϣ
 * 11��AnonymousAuthenticationFilter
 * 		ǰ�涼û�гɹ���½��ִ��������½����SecurityContext����֤��Ϣ
 * 12��SessionManagementFilter
 * 		1.session�̻�����-ͨ��session-fixation-protection���� 
 * 		2.session��������-ͨ��concurrency-control���� �������Ҫ�ڹ�������6��7ִ�еģ�
 * 		��session��صĹ��������ڲ�ά����һ��SessionAuthenticationStrategy���������ʹ�ã�
 * 		��������ֹsession-fixation protection attack���Լ�����ͬһ�û���������Ự������
 * 		���¼��֤����ʱ����һ�����־û��û���¼��Ϣ�����Ա��浽session�У�Ҳ���Ա��浽cookie����redis�С�
 * 13��ExceptionTranslationFilter
 * 		�쳣���أ��䴦��Filter���󲿷֣�ֻ�����������Ľڵ㲢��ֻ����AuthenticationException
 * 		��AccessDeniedException�����쳣��AuthenticationExceptionָ����δ��¼״̬�·����ܱ�����Դ��
 * 		AccessDeniedExceptionָ���ǵ�½�˵�������Ȩ�޲��㣨������ͨ�û����ʹ���Ա���棩�� 
 * 14��FilterSecurityInterceptor
 * 		 ���filter������Ȩ��֤��FilterSecurityInterceptor�Ĺ������̣�
 * 		FilterSecurityInterceptor��SecurityContextHolder�л�ȡAuthentication����
 * 		Ȼ��ȶ��û�ӵ�е�Ȩ�޺���Դ�����Ȩ�ޡ�ǰ�߿���ͨ��Authentication����ֱ�ӻ�ã�
 * 		����������Ҫ��������֮ǰһֱδ�ᵽ���������ࣺSecurityMetadataSource��AccessDecisionManager��
 * @author LiJunPeng
 *
 */
@Configuration
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer{
	public SecurityWebInitializer() {
		System.out.println("SecurityWebInitializer.constructor()");
	}

	/**
	 * ����Ự������ָ�����Ự������Ҫ����
	 */
	@Override
	protected boolean enableHttpSessionEventPublisher() {
		System.out.println("SecurityWebInitializer.enableHttpSessionEventPublisher()");
		return true;
	}
}
