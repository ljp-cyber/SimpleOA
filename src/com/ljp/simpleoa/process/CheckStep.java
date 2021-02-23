package com.ljp.simpleoa.process;

/**
 * 在Step基础上只是加了checkType属性，
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
