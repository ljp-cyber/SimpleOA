package com.ljp.process;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * �������һ�����裬�൱��ͼ��һ���㣬�������������Ϣ��
 * 1���������֡�������״̬��
 * 2����һ�������ӱ����á��������ơ�������Ӧ���衱������LinkedHashMap���
 * 3���ɱ����һ�������Ӳ��ԣ�Ĭ��Ϊ������һ�����Ӧ����
 * 4���ɱ�Ĵ����˲���
 * ����һЩgetter��setter�⻹����Ҫ�ķ�����
 * getNextStep()���ݲ��Ի�ȡ��һ������ֱ�ӻ�ȡĬ�ϵĵ�һ��������Ĭ�Ͽ���Ϊ�յģ������ɱ��ʵ�֣�
 * getOperations()��ȡ��������
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
