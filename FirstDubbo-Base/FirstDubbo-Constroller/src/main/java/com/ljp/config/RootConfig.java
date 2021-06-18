package com.ljp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * RootConfig扫描加载持久层的一些bean
 * @author LiJunPeng
 *
 */
@Configuration
@EnableAspectJAutoProxy
@ImportResource("classpath:dubbo-provider.xml")
//@ComponentScan( 
//		basePackageClasses= {
//				}, 
//		excludeFilters = {
//				@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) 
//		}
//)
public class RootConfig {
	public RootConfig() {
		System.out.println("RootConfig.constructor()");
	}

//	@Bean
//	public ReceiptsDBChange receiptsDBChange() {
//		return new ReceiptsDBChange();
//	}
}
