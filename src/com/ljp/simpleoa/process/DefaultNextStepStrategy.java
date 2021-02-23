package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;

/**
 * Ĭ�ϵ���һ�����ԣ�ֻ���ݲ������ӱ��ȡ��һ��
 * @author LiJunPeng
 *
 */
public class DefaultNextStepStrategy implements NextStepStrategy {

	/**
	 * �������ÿ���Ϊ��
	 */
	@Override
	public Step getNextStep(String operation,LinkedHashMap<String, Step> nextStepMap, Object arg) {
		return nextStepMap.get(operation);
	}

}
