package com.ljp.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * springmvc�������ļ�
 * @author LiJunPeng
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	public WebAppInitializer() {
		System.out.println("SimpleOAWebAppInitializer.constructor()");
	}


	/**
	 * �־ò�����
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.out.println("SimpleOAWebAppInitializer.getRootConfigClasses()");
		return new Class<?>[] {
			RootConfig.class
			,SecurityConfig.class
			};
	}

	/**
	 * web����
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("SimpleOAWebAppInitializer.getServletConfigClasses()");
		return new Class<?>[] {WebConfig.class};
	}

	/**
	 * ���ô���·������/��Ϊ������������
	 */
	@Override
	protected String[] getServletMappings() {
		System.out.println("SimpleOAWebAppInitializer.getServletMappings()");
		return new String[] {"/"};
	}
	
}
