package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;

import com.ljp.simpleoa.Constant;

/**
 * 默认的审核步骤策略，默认需要检测
 * 不需要检测怎跳转到操作为null的的步骤
 * 重写isNeedToCheck()即可
 * @author LiJunPeng
 *
 */
public class DefaultNextCheckStepStrategy extends DefaultNextStepStrategy implements NextCheckStepStrategy {

	@Override
	public Step getNextStep(String operation, LinkedHashMap<String, Step> nextStepMap, Object arg) {
		if(operation.equals(Constant.STEP_CHECK_PAST)&&!isNeedToCheck(arg)) {
			return nextStepMap.get(null);
		}
		return super.getNextStep(operation, nextStepMap, arg);
	}

	@Override
	public boolean isNeedToCheck(Object arg) {
		return true;
	}



}
