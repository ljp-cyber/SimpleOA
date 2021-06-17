package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProcessBuilder {
	private Process res;
	
	// ��ʼ������ʹ�õ�ͼ
	private LinkedHashMap<String, Map<String, String>> map;
	private LinkedHashMap<String, Step> stepMap;
	
	
	public ProcessBuilder() {
		map = new LinkedHashMap<String, Map<String, String>>();
		stepMap = new LinkedHashMap<>();
	}
	
	public Process bulid() throws Exception {
		if(res!=null) {
			synchronized (this) {
				if(res!=null) {
					res=new Process(stepMap);
					init();
					res.setStateMap();
				}
			}
		}
		return res;
	}
	
	public ProcessBuilder addStep(Step step, Map<String, String> linkMap) {
		this.stepMap.put(step.getName(), step);
		this.map.put(step.getName(), linkMap);
		return this;
	}

	public void show() {
		System.out.println(map);
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
	}

	/**
	 * �������ӱ�Ͳ�����ʼ����
	 * @param stepMap
	 * @param map
	 * @throws Exception
	 */
	public void init(LinkedHashMap<String, Step> stepMap, LinkedHashMap<String, Map<String, String>> map)
			throws Exception {
		//TODO δʹ��
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
	}
	
	
	

	/**
	 * ������
	 * @return
	 */
	public boolean checkError() {
		//TODO δ����δʹ��
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
	 * ���ݲ������ӱ����ģ��
	 */
	public void updateProfile() {
		//δ����δʹ��
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
