package com.ljp.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ljp.bean.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-mapper.xml")
public class TestMapper {

	@Autowired
	DepartmentMapper departmentMapper;
	
	@Test
	public void TestDepartmentMpaaer(){
		List<Department> list = departmentMapper.selectByExample(null);
		
		for (Department department : list) {
			System.out.println(department);
		}
	}
	
}
