package com.ljp.process;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 流程里的一个步骤，相当于图的一个点，里面存了以下信息：
 * 1、流程名字、别名、状态名
 * 2、下一步的连接表，采用“操作名称”：“对应步骤”的排序LinkedHashMap存放
 * 3、可变的下一步的连接策略，默认为根据下一步表对应连接
 * 4、可变的处理人策略
 * 除了一些getter、setter外还有重要的方法：
 * getNextStep()根据策略获取下一步、或直接获取默认的第一个（参数默认可以为空的，可以由别的实现）
 * getOperations()获取操作集合
 * @author LiJunPeng
 *
 */
public class Step {
	
	private DealerStrategy dealerStrategy;
	
	private String name;
	
	private String alias;
	
	private LinkedHashMap<String, Step> nextStepMap;

	private String state;

	private NextStepStrategy nextStepStrategy;

	public Step(String name, String state, DealerStrategy dealerStrategy,
			NextStepStrategy nextStepStrategy) {
		super();
		this.name = name;
		this.alias = name;
		this.state = state;
		this.dealerStrategy = dealerStrategy;
		this.nextStepStrategy = nextStepStrategy;
	}
	
	public Step(String name, String state) {
		super();
		this.name = name;
		this.alias = name;
		this.state = state;
	}

	public String getAlias() {
		return alias;
	}

	public Object getDealer(Object arg) {
		if(dealerStrategy==null) return null;
		return  dealerStrategy.getDealer(arg);
	}

	public DealerStrategy getDealerStrategy() {
		return dealerStrategy;
	}

	public String getName() {
		return name;
	}

	public LinkedHashMap<String, Step> getNextStepMap() {
		return nextStepMap;
	}

	public Step getNextStep(String operation,Object arg) {
		if(nextStepStrategy==null) return null;
		return nextStepStrategy.getNextStep(operation, nextStepMap, arg);
	}
	
	public Step getNextStep(Object arg) {
		if(nextStepStrategy==null) return null;
		return nextStepStrategy.getNextStep(nextStepMap.keySet().iterator().next(),nextStepMap,arg);
	}

	public NextStepStrategy getNextStepStrategy() {
		return nextStepStrategy;
	}

	public Set<String> getOperations() {
		return nextStepMap.keySet();
	}

	public String getState() {
		return state;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setDealerStrategy(DealerStrategy DealerStrategy) {
		this.dealerStrategy = DealerStrategy;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setNextStepMap(LinkedHashMap<String, Step> nextStepMap) {
		this.nextStepMap = nextStepMap;
	}
	
	public void setNextStepStrategy(NextStepStrategy nextStepStrategy) {
		this.nextStepStrategy = nextStepStrategy;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Step [name=" + name + ", alias=" + alias + ", operation=" + nextStepMap.keySet()+ ", state=" + state + "]";
	}
}
