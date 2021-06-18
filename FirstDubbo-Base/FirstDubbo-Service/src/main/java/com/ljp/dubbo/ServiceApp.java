package com.ljp.dubbo;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ljp.bean.Department;
import com.ljp.mapperInterface.DepartmentMapper;
import com.ljp.service.DepartmentService;
import com.ljp.serviceInterface.DepartmentServiceFace;

public class ServiceApp {
	public static void main(String[] args) throws Exception {
		 //��ȡ�����ļ�
	    ClassPathXmlApplicationContext applicationContext = 
	    		new ClassPathXmlApplicationContext(new String[]{"classpath:dubbo-provider.xml"});
	    applicationContext.start();
	    System.out.println("service��������ע��");
	    DepartmentServiceFace departmentService=new DepartmentService(applicationContext.getBean(DepartmentMapper.class));
	    List<Department> list = departmentService.queryAll();
	    for (Department department : list) {
			System.out.println(department);
		}
	    //ʹ�߳�����
	    System.in.read();
	    applicationContext.close();
	}
}
