package com.ljp.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MapperApp {
	public static void main(String[] args) throws Exception {
		 //��ȡ�����ļ�
	    ClassPathXmlApplicationContext applicationContext = 
	    		new ClassPathXmlApplicationContext(new String[]{"classpath:dubbo-provider.xml"});
	    applicationContext.start();
	    System.out.println("provider������ע��");
	    //ʹ�߳�����
	    System.in.read();
	    applicationContext.close();
	}
}
