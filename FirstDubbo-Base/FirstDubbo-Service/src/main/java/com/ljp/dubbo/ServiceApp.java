package com.ljp.dubbo;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ljp.bean.Department;
import com.ljp.mapperInterface.DepartmentMapper;
import com.ljp.service.DepartmentService;
import com.ljp.serviceInterface.DepartmentServiceFace;

public class ServiceApp {
	public static void main(String[] args) throws Exception {
		 //读取配置文件
	    ClassPathXmlApplicationContext applicationContext = 
	    		new ClassPathXmlApplicationContext(new String[]{"classpath:dubbo-provider.xml"});
	    applicationContext.start();
	    System.out.println("service消费者已注册");
	    DepartmentServiceFace departmentService=new DepartmentService(applicationContext.getBean(DepartmentMapper.class));
	    List<Department> list = departmentService.queryAll();
	    for (Department department : list) {
			System.out.println(department);
		}
	    //使线程阻塞
	    System.in.read();
	    applicationContext.close();
	}
}
