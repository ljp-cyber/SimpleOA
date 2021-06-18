package com.ljp.serviceInterface;

import java.util.List;

import com.ljp.bean.Worker;

public interface WorkerServiceFace {

	List<Worker> queryAll();

	Worker queryByPrimaryKey(Integer workId);

	Worker queryOne(String Sn);

	int updateOne(Worker worker);

	int addOne(Worker worker);

	int removeOne(String workerSn);

	int removeSome(String sn);

	Worker getDepartmentManager(Worker worker);

	Worker getDepartmentManager(Integer workerId);

	Worker getGeneralManager();

	Worker getFinancialer();

}