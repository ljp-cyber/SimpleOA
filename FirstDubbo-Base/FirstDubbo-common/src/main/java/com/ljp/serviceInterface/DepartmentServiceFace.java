package com.ljp.serviceInterface;

import java.util.List;

import com.ljp.bean.Department;

public interface DepartmentServiceFace {

	List<Department> queryAll();

	Department queryByKey(int departmentId);

	List<Department> queryByPage(int starRow, int pageSize);

	Department queryBySn(String sn);

	int updateOne(Department department);

	int insertOne(Department department);

	int removeOne(String sn);

	int removeSome(String sn);

}