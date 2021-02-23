package com.ljp.simpleoa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * WebConfig加载关于web的一些bean
 * 
 * @EnableAutoConfiguration 注解，会自动读取 application.properties 或 application.yml 文件中的配置,
 * 		spring boot的会用此默认配置，一般情况下够用了，看源码得知此注解会在没有WebMvcConfigurationSupport才有用
 * 
 * @EnableWebMvc 此注解实现了WebMvcConfigurationSupport，这里继承的话都没有效果的；
 * 		此注解一般和WebMvcConfigurerAdapter配合使用，但是在spring5 已弃用
 * 
 * WebMvcConfigurer只是基础配置，WebMvcConfigurationSupport或DelegatingWebMvcConfiguration有加强配置
 * 
 * @author LiJunPeng
 *
 */
@Configuration
@ComponentScan(basePackageClasses = { 
		com.ljp.simpleoa.controller.LoginController.class 
		})
public class WebConfig extends WebMvcConfigurationSupport {

	public WebConfig() {
		System.out.println("WebConfig.constructor()");
	}

	/**
	 * 视图解析器bean
	 * 
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() {
		System.out.println("WebConfig.viewResolver()");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/");// controller的前缀
		resolver.setSuffix(".jsp");// controller的后缀
		resolver.setExposeContextBeansAsAttributes(true);// 显露上下文在Attributes里
		return resolver;
	}

	/**
	 * 配置静态页面加载器，手动配置，不配置只可以访问WebContent下的jsp页面
	 * 如果想禁止直接访问jsp，可以把jsp放进WEB-INF文件夹（其他夹可以？）对外不可见，或用filter拦截
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("WebConfig.addResourceHandlers()");

		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
		registry.addResourceHandler("/vendor/**").addResourceLocations("/vendor/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		super.addResourceHandlers(registry);
	}

//	/**
//	 * 启用默认路径解析器，会把webcontent所有资源加入来，包括jsp页面，但是不会经过视图解析器，不会加载数据，
//	 * 		但是session和application的数据会加载
//	 * 配置了@EnableWebMvc无效
//	 */
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		System.out.println("WebConfig.configureDefaultServletHandling()");
//		System.out.println("configurer:"+configurer);
//		super.configureDefaultServletHandling(configurer);
//		configurer.enable();
//	}

}
