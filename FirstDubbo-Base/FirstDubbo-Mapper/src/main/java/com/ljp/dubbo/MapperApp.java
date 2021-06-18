package com.ljp.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MapperApp {
	public static void main(String[] args) throws Exception {
		 //读取配置文件
	    ClassPathXmlApplicationContext applicationContext = 
	    		new ClassPathXmlApplicationContext(new String[]{"classpath:dubbo-provider.xml"});
	    applicationContext.start();
	    System.out.println("provider服务已注册");
	    //使线程阻塞
	    System.in.read();
	    applicationContext.close();
	}
}
