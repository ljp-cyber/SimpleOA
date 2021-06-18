package com.ljp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljp.bean.Worker;
import com.ljp.bean.WorkerExample;
import com.ljp.mapperInterface.DepartmentMapper;
import com.ljp.mapperInterface.WorkerMapper;
import com.ljp.serviceInterface.LoginServiceFace;

@Service
public class LoginService implements LoginServiceFace {

	@Autowired
	private WorkerMapper workerMapper;
	@Autowired
	DepartmentMapper departmentMapper;
	
	
	@Override
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
	
	@Override
	public Worker changePasswork(Worker worker,String oldPw,String newPw) {
		System.out.println(worker);
		System.out.println(oldPw);
		System.out.println(newPw);
		if(oldPw.equals(worker.getWorkerPw())){
			WorkerExample example=new WorkerExample();
			WorkerExample.Criteria criteria = example.createCriteria();
			criteria.andWorkerSnEqualTo(worker.getWorkerSn());
			int count = workerMapper.updateByExampleSelective(worker, example);
			if(count>0) {
				worker.setWorkerPw(newPw);
				return worker;
			}
		}
		return null;
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
	
}
