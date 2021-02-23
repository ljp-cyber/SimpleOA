package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;

/**
 * 默认的下一步策略，只根据操作连接表获取下一步
 * @author LiJunPeng
 *
 */
public class DefaultNextStepStrategy implements NextStepStrategy {

	/**
	 * 参数无用可以为空
	 */
	@Override
	public Step getNextStep(String operation,LinkedHashMap<String, Step> nextStepMap, Object arg) {
		return nextStepMap.get(operation);
	}

}
