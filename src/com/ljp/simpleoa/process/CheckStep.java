package com.ljp.simpleoa.process;

/**
 * ��Step������ֻ�Ǽ���checkType���ԣ�
 * @author LiJunPeng
 *
 */
public class CheckStep extends Step {

	private String checkType;

	public CheckStep(String name, String state, String checkType, DealerStrategy dealerStrategy,
			NextCheckStepStrategy nextCheckStepStrategy) {
		super(name, state, dealerStrategy, nextCheckStepStrategy);
		this.checkType = checkType;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

}
