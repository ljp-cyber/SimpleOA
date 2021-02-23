package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;
import java.util.Map;

public class Process {
	public static void main(String[] args) throws Exception {

		Process process = new Process();
		try {
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_CREAT),
					StepUtils.getDefaultMap(StepUtils.STEP_CREAT));
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_MODIFY), StepUtils.creatNewDefaultModifyMap("����"));
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_PAY), StepUtils.getDefaultMap(StepUtils.STEP_PAY));
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_END), StepUtils.getDefaultMap(StepUtils.STEP_END));
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_FINISH),
					StepUtils.getDefaultMap(StepUtils.STEP_FINISH));
			// check����
			process.addStep(StepUtils.creatNewDefaultCheckStep("����", "�����������"),
					StepUtils.creatNewDefaultCheckMap("����"));
			process.addStep(StepUtils.creatNewDefaultCheckStep("����", "���ܾ������"),
					StepUtils.getDefaultMap(StepUtils.STEP_CHECK));
		} catch (Exception e) {
			e.printStackTrace();
		}
		process.init();
		process.show();

		process.nextStep("����", null, null);
		process.nextStepByState(null, null, null);
		process.nextStep("�޸�", "�޸�", null);
		process.nextStep("�޸�", "�ύ", null);
		process.nextStep("����", "���", null);
		process.nextStep("����", "�ܾ�", null);
		process.nextStep("����", "ͨ��", null);
		process.nextStep("����", "ͨ��", null);
		process.nextStep("���", "���", null);
	}

	// ��ʼ������ʹ�õ�ͼ
	private LinkedHashMap<String, Map<String, String>> map;

	private LinkedHashMap<String, Step> stepMap;
	private LinkedHashMap<String, String> stateMap;

	public Process() {
		map = new LinkedHashMap<String, Map<String, String>>();
		stepMap = new LinkedHashMap<>();
	}

	/**
	 * �����ɺ�Ҫִ��checkError() �����ִ��init
	 * @param step
	 * @param linkMap
	 */
	public void addStep(Step step, Map<String, String> linkMap) {
		this.stepMap.put(step.getName(), step);
		this.map.put(step.getName(), linkMap);
	}
	
	public boolean isModify(String operation) {
		if(StepUtils.STEP_MODIFY_MODIFY.equals(operation)) {
			return true;
		}
		return false;
	}

	public boolean checkError() {
		System.out.println("Process.checkError()");
		for (String stepName : map.keySet()) {
			if (stepMap.get(stepName) == null) {
				System.out.println(stepName + "����Ϊ�գ�");
				return false;
			}
			Map<String, String> map2 = map.get(stepName);
			for (String key : map2.keySet()) {
				String string = map2.get(key);
				if (stepMap.get(string) == null) {
					System.out.println(stepName + ":" + key + ":" + string + "����Ϊ�գ�");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * ������ӵ�step��map��ʼ��
	 * @throws Exception
	 */
	public void init() throws Exception {
		for (String stepName : stepMap.keySet()) {
			Map<String, String> linkMap = map.get(stepName);

			LinkedHashMap<String, Step> linkedHashMap = new LinkedHashMap<>();
			for (String operation : linkMap.keySet()) {
				linkedHashMap.put(operation, stepMap.get(linkMap.get(operation)));
			}
			stepMap.get(stepName).setNextStepMap(linkedHashMap);
		}
		setStateMap();
	}

	/**
	 * �������ӱ�Ͳ�����ʼ��
	 * @param stepMap
	 * @param map
	 * @throws Exception
	 */
	public void init(LinkedHashMap<String, Step> stepMap, LinkedHashMap<String, Map<String, String>> map)
			throws Exception {
		for (String stepName : stepMap.keySet()) {
			Map<String, String> linkMap = map.get(stepName);

			LinkedHashMap<String, Step> linkedHashMap = new LinkedHashMap<>();
			for (String operation : linkMap.keySet()) {
				linkedHashMap.put(operation, stepMap.get(linkMap.get(operation)));
			}
			stepMap.get(stepName).setNextStepMap(linkedHashMap);
		}
		this.map = map;
		this.stepMap = stepMap;
		setStateMap();
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
	 * ����������Ҫ����״̬��
	 * @param oldStepName
	 * @param newStepName
	 * @throws Exception
	 */
	public void reName(String oldStepName, String newStepName) throws Exception {
		//TODO �����ּ��û��ʵ��
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
	 * ״̬���������ظ�������ʱҪ�����
	 * @throws Exception
	 */
	public void setStateMap() throws Exception {
		stateMap = new LinkedHashMap<>();
		for (String stepName : stepMap.keySet()) {
			String temp = stepMap.get(stepName).getState();
			if (stateMap.containsKey(temp)) {
				throw new Exception("�����ظ���״̬");
			}
			stateMap.put(temp, stepName);
		}
	}

	public void show() {
		System.out.println(map);
	}

	public Step thisStep(String stepName) {
		return stepMap.get(stepName);
	}
	
	public Step thisStepByState(String state) {
		return stepMap.get(stateMap.get(state));
	}

	/**
	 * ���ݲ������ӱ����ģ��
	 */
	public void updateProfile() {
		map = new LinkedHashMap<>();
		for (String stepName : stepMap.keySet()) {
			Map<String, String> linkMap = new LinkedHashMap<>();
			Step step = stepMap.get(stepName);
			LinkedHashMap<String, Step> nextStepMap = step.getNextStepMap();
			for (String operation : nextStepMap.keySet()) {
				linkMap.put(operation, nextStepMap.get(operation).getName());
			}
			map.put(stepName, linkMap);
		}
	}

}
