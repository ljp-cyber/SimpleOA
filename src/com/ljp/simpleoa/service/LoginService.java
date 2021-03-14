package com.ljp.simpleoa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljp.simpleoa.mapper.DepartmentMapper;
import com.ljp.simpleoa.mapper.WorkerMapper;
import com.ljp.simpleoa.model.Worker;
import com.ljp.simpleoa.model.WorkerExample;
import com.ljp.simpleoa.model.WorkerExample.Criteria;

@Service
public class LoginService {

	@Autowired
	private WorkerMapper workerMapper;
	@Autowired
	DepartmentMapper departmentMapper;
	
	
	public Worker login(Worker worker) {
		if(worker==null)return null;
		WorkerExample workerExample=new WorkerExample();
		WorkerExample.Criteria criteria = workerExample.createCriteria();
		criteria.andWorkerSnEqualTo(worker.getWorkerSn());
		List<Worker> list = workerMapper.selectByExample(workerExample);
		if(list==null||list.size()==0) {
			return null;
		}
		Worker loginWorker=list.get(0);
		if(worker.getWorkerPw().equals(loginWorker.getWorkerPw())) {
			return loginWorker;
		}
		return null;
	}
	
	public Worker changePasswork(Worker worker,String oldPw,String newPw) {
		System.out.println(worker);
		System.out.println(oldPw);
		System.out.println(newPw);
		if(oldPw.equals(worker.getWorkerPw())){
			Worker newWorker = new Worker();
			newWorker.setWorkerPw(newPw);
			WorkerExample example=new WorkerExample();
			Criteria criteria = example.createCriteria();
			criteria.andWorkerSnEqualTo(worker.getWorkerSn());
			int count = workerMapper.updateByExampleSelective(newWorker, example);
			if(count>0) {
				worker.setWorkerPw(newPw);
				return worker;
			}
		}
		return null;
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
	
}
