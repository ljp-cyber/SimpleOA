package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;

public interface NextStepStrategy {
	public Step getNextStep(String operation, LinkedHashMap<String, Step> nextStepMap, Object arg);
}
