package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;

public class Process {

	private LinkedHashMap<String, Step> stepMap;
	//状态-节点名字 的映射
	private LinkedHashMap<String, String> stateMap;

	public Process() {
		stepMap = new LinkedHashMap<>();
	}
	
	public Process(LinkedHashMap<String, Step> stepMap) {
		this.stepMap=stepMap;
	}

	public Step nextStep(String thisStepName, String operation, Object arg) {
		if (thisStep(thisStepName) != null) {
			Step nextStep = thisStep(thisStepName).getNextStep(operation, arg);
			System.out.println(nextStep);
			return nextStep;
		} else {
			System.out.println("null");
			return null;
		}
	}

	public Step nextStepByState(String thisState, String operation, Object arg) {
		return nextStep(stateMap.get(thisState), operation, arg);
	}

	/**
	 * 更改名字需要更新状态表
	 * @param oldStepName
	 * @param newStepName
	 * @throws Exception
	 */
	public void reName(String oldStepName, String newStepName) throws Exception {
		//TODO 新名字检测没有实现
		Step step = stepMap.get(oldStepName);
		step.setName(newStepName);
		stepMap.remove(oldStepName);
		stepMap.put(newStepName, step);
		setStateMap();
	}

	public void reNextOfPass(String stepName, String newNextOfPassStepName) {
		StepUtils.changeNextOfPass(stepMap.get(stepName), stepMap.get(newNextOfPassStepName));
	}

	public void reState(String stepName, String newStateName) throws Exception {
		stepMap.get(stepName).setState(newStateName);
		setStateMap();
	}

	/**
	 * 状态名不可以重复，设置时要做检测
	 * @throws Exception
	 */
	public void setStateMap() throws Exception {
		stateMap = new LinkedHashMap<>();
		for (String stepName : stepMap.keySet()) {
			String temp = stepMap.get(stepName).getState();
			if (stateMap.containsKey(temp)) {
				throw new Exception("包含重复的状态");
			}
			stateMap.put(temp, stepName);
		}
	}

	public boolean isModify(String operation) {
		if(StepUtils.STEP_MODIFY_MODIFY.equals(operation)) {
			return true;
		}
		return false;
	}

	public Step thisStep(String stepName) {
		return stepMap.get(stepName);
	}
	
	public Step thisStepByState(String state) {
		return stepMap.get(stateMap.get(state));
	}


	
	public static void main(String[] args) throws Exception {

		Process process;
		ProcessBuilder pb=new ProcessBuilder();
		try {
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_CREAT),
					StepUtils.getDefaultMap(StepUtils.STEP_CREAT));
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_MODIFY), StepUtils.creatNewDefaultModifyMap("初审"));
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_PAY), StepUtils.getDefaultMap(StepUtils.STEP_PAY));
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_END), StepUtils.getDefaultMap(StepUtils.STEP_END));
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_FINISH),
					StepUtils.getDefaultMap(StepUtils.STEP_FINISH));
			// check流程
			pb.addStep(StepUtils.creatNewDefaultCheckStep("初审", "待部经理审核"),
					StepUtils.creatNewDefaultCheckMap("复审"));
			pb.addStep(StepUtils.creatNewDefaultCheckStep("复审", "待总经理审核"),
					StepUtils.getDefaultMap(StepUtils.STEP_CHECK));
		} catch (Exception e) {
			e.printStackTrace();
		}
		pb.show();
		process = pb.bulid();

		process.nextStep("创建", null, null);
		process.nextStepByState(null, null, null);
		process.nextStep("修改", "修改", null);
		process.nextStep("修改", "提交", null);
		process.nextStep("初审", "打回", null);
		process.nextStep("初审", "拒绝", null);
		process.nextStep("初审", "通过", null);
		process.nextStep("复审", "通过", null);
		process.nextStep("打款", "打款", null);
	}

}
