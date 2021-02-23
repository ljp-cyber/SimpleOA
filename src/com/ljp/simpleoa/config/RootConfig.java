package com.ljp.simpleoa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * RootConfig扫描加载持久层的一些bean
 * @author LiJunPeng
 *
 */
@Configuration
@ImportResource("classpath:applicationContext.xml")
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan( 
		basePackageClasses= {
				com.ljp.simpleoa.service.LoginService.class
				}, 
		excludeFilters = {
				@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) 
		}
)
public class RootConfig {
	public RootConfig() {
		System.out.println("RootConfig.constructor()");
	}

//	@Bean
//	public ReceiptsDBChange receiptsDBChange() {
//		return new ReceiptsDBChange();
//	}
}
