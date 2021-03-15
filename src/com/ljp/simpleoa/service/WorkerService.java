package com.ljp.simpleoa.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ljp.simpleoa.Constant;
import com.ljp.simpleoa.mapper.DepartmentMapper;
import com.ljp.simpleoa.mapper.WorkerMapper;
import com.ljp.simpleoa.model.Department;
import com.ljp.simpleoa.model.Worker;
import com.ljp.simpleoa.model.WorkerExample;
import com.ljp.simpleoa.model.WorkerExample.Criteria;

@Service
public class WorkerService {
	
	@Autowired
	WorkerMapper workerMapper;
	
	@Autowired
	DepartmentMapper departmentMapper;
	public List<Worker> queryByDepartment(Worker dm) {
		WorkerExample workerExample = new WorkerExample();
		Criteria criteria = workerExample.createCriteria();
		criteria.andDepartmentIdEqualTo(dm.getDepartmentId());
		List<Worker> list = workerMapper.selectByExample(workerExample);
		Map<Integer, Department> departmentList = departmentMapper.selectByExampleToMap(null);
		for (int i = 0; i < list.size(); i++) {
			Worker worker = list.get(i);
			Integer departmentId = worker.getDepartmentId();
			Department department = departmentList.get(departmentId);
			worker.setDepartment(department);
		}
		return list;
	}
	public List<Worker> queryAll() {
		List<Worker> list = workerMapper.selectByExample(null);
		Map<Integer, Department> departmentList = departmentMapper.selectByExampleToMap(null);
		for (int i = 0; i < list.size(); i++) {
			Worker worker = list.get(i);
			Integer departmentId = worker.getDepartmentId();
			Department department = departmentList.get(departmentId);
			worker.setDepartment(department);
		}
		return list;
	}
	
	public Worker queryByPrimaryKey(Integer workId) {
		return workerMapper.selectByPrimaryKey(workId);
	}
	
	public Worker queryOne(String Sn) {
		WorkerExample example=new WorkerExample();
		Criteria criteria = example.createCriteria();
		criteria.andWorkerSnEqualTo(Sn);
		List<Worker> list = workerMapper.selectByExample(example);
		if(list!=null&&list.size()!=0) {
			Worker worker=list.get(0);
			worker.setDepartment(departmentMapper.selectByPrimaryKey(worker.getDepartmentId()));
			return worker;
		}
		return null;
	}
	
	public int updateOne(Worker worker) {
		WorkerExample example=new WorkerExample();
		Criteria criteria = example.createCriteria();
		criteria.andWorkerSnEqualTo(worker.getWorkerSn());
		int count = workerMapper.updateByExampleSelective(worker, example);
		return count;
	}
	
	@Transactional(propagation=Propagation.NESTED)
	public int addOne(Worker worker) {
		System.out.println(worker.toString());
		int count = 0;
		try {
			count = workerMapper.insertSelective(worker);
		}catch(Exception e) {
			e.printStackTrace();
		}
//		count=1/0;
		return count;
	}
	
	public int removeOne(String workerSn) {
		WorkerExample example=new WorkerExample();
		Criteria criteria = example.createCriteria();
		criteria.andWorkerSnEqualTo(workerSn);
		int count = workerMapper.deleteByExample(example);
		return count;
	}
	
	public int removeSome(String sn) {
		WorkerExample example=new WorkerExample();
		Criteria criteria = example.createCriteria();
		criteria.andWorkerSnIn(Arrays.asList( sn.split(",")));
		int count = workerMapper.deleteByExample(example);
		return count;
	}
	
	public Worker getDepartmentManager(Worker worker) {
		if(worker==null) return null;
		WorkerExample example = new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andDepartmentIdEqualTo(worker.getDepartmentId());
		criteria.andPostEqualTo(Constant.POST_DM);
		List<Worker> list = workerMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public Worker getDepartmentManager(Integer workerId) {
		WorkerExample example = new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andDepartmentIdEqualTo(workerMapper.selectByPrimaryKey(workerId).getDepartmentId());
		criteria.andPostEqualTo(Constant.POST_DM);
		List<Worker> list = workerMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public Worker getGeneralManager(){
		WorkerExample example = new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andPostEqualTo(Constant.POST_GM);
		List<Worker> list = workerMapper.selectByExample(example);
//		Worker list = workerMapper.selectByPrimaryKey(9);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public Worker getFinancialer() {
		WorkerExample example = new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andPostEqualTo(Constant.POST_FM);
		List<Worker> list = workerMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
