package com.ljp.simpleoa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * WebConfig���ع���web��һЩbean
 * 
 * @EnableAutoConfiguration ע�⣬���Զ���ȡ application.properties �� application.yml �ļ��е�����,
 * 		spring boot�Ļ��ô�Ĭ�����ã�һ������¹����ˣ���Դ���֪��ע�����û��WebMvcConfigurationSupport������
 * 
 * @EnableWebMvc ��ע��ʵ����WebMvcConfigurationSupport������̳еĻ���û��Ч���ģ�
 * 		��ע��һ���WebMvcConfigurerAdapter���ʹ�ã�������spring5 ������
 * 
 * WebMvcConfigurerֻ�ǻ������ã�WebMvcConfigurationSupport��DelegatingWebMvcConfiguration�м�ǿ����
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
	 * ��ͼ������bean
	 * 
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() {
		System.out.println("WebConfig.viewResolver()");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/");// controller��ǰ׺
		resolver.setSuffix(".jsp");// controller�ĺ�׺
		resolver.setExposeContextBeansAsAttributes(true);// ��¶��������Attributes��
		return resolver;
	}

	/**
	 * ���þ�̬ҳ����������ֶ����ã�������ֻ���Է���WebContent�µ�jspҳ��
	 * ������ֱֹ�ӷ���jsp�����԰�jsp�Ž�WEB-INF�ļ��У������п��ԣ������ⲻ�ɼ�������filter����
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
//	 * ����Ĭ��·�������������webcontent������Դ������������jspҳ�棬���ǲ��ᾭ����ͼ������������������ݣ�
//	 * 		����session��application�����ݻ����
//	 * ������@EnableWebMvc��Ч
//	 */
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		System.out.println("WebConfig.configureDefaultServletHandling()");
//		System.out.println("configurer:"+configurer);
//		super.configureDefaultServletHandling(configurer);
//		configurer.enable();
//	}

}
