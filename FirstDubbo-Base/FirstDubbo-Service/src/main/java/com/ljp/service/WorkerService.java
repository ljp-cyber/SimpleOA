package com.ljp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljp.bean.Department;
import com.ljp.bean.Worker;
import com.ljp.bean.WorkerExample;
import com.ljp.common.Constant;
import com.ljp.mapperInterface.DepartmentMapper;
import com.ljp.mapperInterface.WorkerMapper;
import com.ljp.serviceInterface.WorkerServiceFace;

@Service
public class WorkerService implements WorkerServiceFace {
	
	@Autowired
	WorkerMapper workerMapper;
	
	@Autowired
	DepartmentMapper departmentMapper;
	
	@Override
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
	
	@Override
	public Worker queryByPrimaryKey(Integer workId) {
		return workerMapper.selectByPrimaryKey(workId);
	}
	
	@Override
	public Worker queryOne(String Sn) {
		WorkerExample example=new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andWorkerSnEqualTo(Sn);
		List<Worker> list = workerMapper.selectByExample(example);
		if(list!=null&&list.size()!=0) {
			Worker worker=list.get(0);
			worker.setDepartment(departmentMapper.selectByPrimaryKey(worker.getDepartmentId()));
			return worker;
		}
		return null;
	}
	
	@Override
	public int updateOne(Worker worker) {
		WorkerExample example=new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andWorkerSnEqualTo(worker.getWorkerSn());
		int count = workerMapper.updateByExampleSelective(worker, example);
		return count;
	}
	
	@Override
	public int addOne(Worker worker) {
		System.out.println(worker.toString());
		int count = workerMapper.insertSelective(worker);
		return count;
	}
	
	@Override
	public int removeOne(String workerSn) {
		WorkerExample example=new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andWorkerSnEqualTo(workerSn);
		int count = workerMapper.deleteByExample(example);
		return count;
	}
	
	@Override
	public int removeSome(String sn) {
		WorkerExample example=new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andWorkerSnIn(Arrays.asList( sn.split(",")));
		int count = workerMapper.deleteByExample(example);
		return count;
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public Worker getGeneralManager(){
		WorkerExample example = new WorkerExample();
		WorkerExample.Criteria criteria = example.createCriteria();
		criteria.andPostEqualTo(Constant.POST_GM);
		List<Worker> list = workerMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
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
