package com.ljp.serviceInterface;

import com.ljp.bean.Worker;

public interface LoginServiceFace {

	Worker login(Worker worker);

	Worker changePasswork(Worker worker, String oldPw, String newPw);

	Worker queryOne(String Sn);

}