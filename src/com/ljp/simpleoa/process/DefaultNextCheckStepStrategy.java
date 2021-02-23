package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;

import com.ljp.simpleoa.Constant;

/**
 * Ĭ�ϵ���˲�����ԣ�Ĭ����Ҫ���
 * ����Ҫ�������ת������Ϊnull�ĵĲ���
 * ��дisNeedToCheck()����
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
