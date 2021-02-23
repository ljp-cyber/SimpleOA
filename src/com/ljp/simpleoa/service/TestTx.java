package com.ljp.simpleoa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ljp.simpleoa.model.Worker;

@Component
public class TestTx {

	@Autowired
	WorkerService workerService;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void test() {
		List<Worker> before = workerService.queryAll();
		Worker worker1 = before.get(8);
		worker1.setWorkerName("≤‚ ‘√˚◊÷1");
		workerService.updateOne(worker1);
		try {
			Worker worker = new Worker();
			worker.setWorkerName("ªÿπˆ≤‚ ‘");
			worker.setWorkerSn("12314");
			worker.setWorkerPw("1234");
			worker.setDepartmentId(3);
			workerService.addOne(worker);
		}catch (Exception e) {
			e.printStackTrace();
		}
		worker1.setWorkerName("≤‚ ‘√˚◊÷2");
		workerService.updateOne(worker1);
		//int a=1/0;
	}
}
