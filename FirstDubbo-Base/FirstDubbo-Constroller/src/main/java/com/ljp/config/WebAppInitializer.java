package com.ljp.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * springmvc主配置文件
 * @author LiJunPeng
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	public WebAppInitializer() {
		System.out.println("SimpleOAWebAppInitializer.constructor()");
	}


	/**
	 * 持久层配置
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
	 * web配置
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("SimpleOAWebAppInitializer.getServletConfigClasses()");
		return new Class<?>[] {WebConfig.class};
	}

	/**
	 * 配置处理路径，“/”为处理所有请求
	 */
	@Override
	protected String[] getServletMappings() {
		System.out.println("SimpleOAWebAppInitializer.getServletMappings()");
		return new String[] {"/"};
	}
	
}
