package com.ljp.simpleoa.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljp.simpleoa.mapper.DepartmentMapper;
import com.ljp.simpleoa.model.Department;
import com.ljp.simpleoa.model.DepartmentExample;
import com.ljp.simpleoa.model.DepartmentExample.Criteria;

@Service
public class DepartmentService {

	@Autowired
	DepartmentMapper departmentMapper;
	
	public List<Department> queryAll() {
		List<Department> list = departmentMapper.selectByExample(null);
		return list;
	}
	
	public Department queryByKey(int departmentId) {
		Department department = departmentMapper.selectByPrimaryKey(departmentId);
		return department;
	}
	
	public List<Department> queryByPage(int starRow,int pageSize){
		DepartmentExample example = new DepartmentExample();
		example.setLimit(starRow, pageSize);
		List<Department> list = departmentMapper.selectByExample(example);
		return list;
	}
	
	public Department queryBySn(String sn) {
		DepartmentExample departmentExample = new DepartmentExample();
		Criteria criteria = departmentExample.createCriteria();
		criteria.andDepartmentSnEqualTo(sn);
		List<Department> list = departmentMapper.selectByExample(departmentExample);
		if(list!=null&&list.size()>0&&list.get(0)!=null) {
			return list.get(0);
		}
		return null;
	}
	
	public int updateOne(Department department) {
		DepartmentExample departmentExample = new DepartmentExample();
		Criteria criteria = departmentExample.createCriteria();
		criteria.andDepartmentSnEqualTo(department.getDepartmentSn());
		int count = departmentMapper.updateByExampleSelective(department, departmentExample);
		return count;
	}
	
	public int insertOne(Department department) {
		int count = departmentMapper.insert(department);
		return count;
	}
	
	public int removeOne(String sn) {
		DepartmentExample departmentExample = new DepartmentExample();
		Criteria criteria = departmentExample.createCriteria();
		criteria.andDepartmentSnEqualTo(sn);
		int count = departmentMapper.deleteByExample(departmentExample);
		
		return count;
	}
	
	public int removeSome(String sn) {
		DepartmentExample departmentExample = new DepartmentExample();
		Criteria criteria = departmentExample.createCriteria();
		criteria.andDepartmentSnIn(Arrays.asList( sn.split(",")));
		int count=0;
		try {
			count = departmentMapper.deleteByExample(departmentExample);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
