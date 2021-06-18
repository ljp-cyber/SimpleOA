package com.ljp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljp.bean.Department;
import com.ljp.bean.DepartmentExample;
import com.ljp.mapperInterface.DepartmentMapper;
import com.ljp.serviceInterface.DepartmentServiceFace;

@Service
public class DepartmentService implements DepartmentServiceFace {

	protected DepartmentMapper departmentMapper;
	
	@Autowired
	public DepartmentService(DepartmentMapper departmentMapper) {
		this.departmentMapper=departmentMapper;
	}
	
	@Override
	public List<Department> queryAll() {
		List<Department> list = departmentMapper.selectByExample(null);
		return list;
	}
	
	@Override
	public Department queryByKey(int departmentId) {
		Department department = departmentMapper.selectByPrimaryKey(departmentId);
		return department;
	}
	
	@Override
	public List<Department> queryByPage(int starRow,int pageSize){
		DepartmentExample example = new DepartmentExample();
		example.setLimit(starRow, pageSize);
		List<Department> list = departmentMapper.selectByExample(example);
		return list;
	}
	
	@Override
	public Department queryBySn(String sn) {
		DepartmentExample departmentExample = new DepartmentExample();
		DepartmentExample.Criteria criteria = departmentExample.createCriteria();
		criteria.andDepartmentSnEqualTo(sn);
		List<Department> list = departmentMapper.selectByExample(departmentExample);
		if(list!=null&&list.size()>0&&list.get(0)!=null) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public int updateOne(Department department) {
		DepartmentExample departmentExample = new DepartmentExample();
		DepartmentExample.Criteria criteria = departmentExample.createCriteria();
		criteria.andDepartmentSnEqualTo(department.getDepartmentSn());
		int count = departmentMapper.updateByExampleSelective(department, departmentExample);
		return count;
	}
	
	@Override
	public int insertOne(Department department) {
		int count = departmentMapper.insert(department);
		return count;
	}
	
	@Override
	public int removeOne(String sn) {
		DepartmentExample departmentExample = new DepartmentExample();
		DepartmentExample.Criteria criteria = departmentExample.createCriteria();
		criteria.andDepartmentSnEqualTo(sn);
		int count = departmentMapper.deleteByExample(departmentExample);
		
		return count;
	}
	
	@Override
	public int removeSome(String sn) {
		DepartmentExample departmentExample = new DepartmentExample();
		DepartmentExample.Criteria criteria = departmentExample.createCriteria();
		criteria.andDepartmentSnIn(Arrays.asList( sn.split(",")));
		int count = departmentMapper.deleteByExample(departmentExample);
		
		return count;
	}
}
