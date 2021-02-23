package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ������Ի�ȡһЩĬ��ͨ�õ����� һЩ���̵Ĵ����˲���Ϊ�ջ򲻷���Ҫ�󣬿��Ե���Stepʵ����setDealerStrategy()���������²���
 * �������ô����˲��ԣ�Step�Ĵ����� ��������ΪObject
 * Ĭ������Ϊ��
 * 1������
 * �������޸�
 * 2���޸�
 * �޸ģ��޸�
 * �ύ�����
 * 3�����
 * ͨ�������
 * �ܾ�����ֹ
 * ��أ��޸�
 * 4�����
 * �����
 * 5�����
 * û��
 * 6����ֹ
 * û��
 * 
 * @author LiJunPeng
 *
 */
public class StepUtils {

	public static final String STEP_CREAT = "����";
	public static final String STEP_MODIFY = "�޸�";
	public static final String STEP_CHECK = "���";
	public static final String STEP_PAY = "���";
	public static final String STEP_FINISH = "���";
	public static final String STEP_END = "��ֹ";
	
	public static final String STEP_CREAT_CREAT="����";
	public static final String STEP_MODIFY_MODIFY = "�޸�";
	public static final String STEP_MODIFY_SUBMIT = "�ύ";
	public static final String STEP_CHECK_PAST = "ͨ��";
	public static final String STEP_CHECK_BACK = "���";
	public static final String STEP_CHECK_REFUSE = "�ܾ�";
	public static final String STEP_PAY_PAY = "���";

	public static final String CHECK_TYPE_PERSONAL = "����";
	public static final String CHECK_TYPE_UNITE = "����";

	public static final String STATE_CREAT = null;
	public static final String STATE_MODIFY = "���ύ";
	public static final String STATE_CHECK = "�����";
	public static final String STATE_PAY = "��֧��";
	public static final String STATE_FINISH = "�Ѵ��";
	public static final String STATE_END = "���ս�";

	/**
	 * ����Ĭ�ϵ���鲽�����ӱ�ָ����ͨ��������������
	 * @param nextOfPass ��ͨ��������������
	 * @return
	 */
	public static Map<String, String> creatNewDefaultCheckMap(String nextOfPass) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(STEP_CHECK_PAST, nextOfPass);
		map.put(STEP_CHECK_BACK, STEP_MODIFY);
		map.put(STEP_CHECK_REFUSE, STEP_END);
		map.put(null, STEP_PAY);
		return map;
	}

	/**
	 * ����Ĭ�ϵ��ύ�������ӱ�ָ�����ύ������������
	 * @param stepOfSubmit ͨ��������
	 * @return
	 */
	public static Map<String, String> creatNewDefaultModifyMap(String stepOfSubmit) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(STEP_MODIFY_MODIFY, STEP_MODIFY);
		map.put(STEP_MODIFY_SUBMIT, stepOfSubmit);
		return map;
	}

	/**
	 * �����ġ�ͨ������������
	 * @param step
	 * @param newStep
	 */
	public static void changeNextOfPass(Step step, Step newStep) {
		step.getNextStepMap().put(STEP_CHECK_PAST, newStep);
	}

	/**
	 * ����Ĭ�ϵ���鲽��
	 * ָ����������״̬����
	 * Ĭ�ϣ��������Ϊ�����ˡ��������˲���Ϊnull��Ĭ�ϵ���һ���������
	 * @param stepName
	 * @param state
	 * @return
	 */
	public static Step creatNewDefaultCheckStep(String stepName, String state) {
		return new CheckStep(stepName, state, CHECK_TYPE_PERSONAL, null, new DefaultNextCheckStepStrategy());
	}

	/**
	 * ����Ĭ�ϵ���鲽��
	 * ָ����������״̬���������˲��ԡ�����һ������
	 * �������Ϊ�����ˡ�
	 * @param stepName
	 * @param state
	 * @param dealerStrategy
	 * @param nextCheckStepStrategy
	 * @return
	 */
	public static Step creatNewDefaultCheckStep(String stepName, String state, DealerStrategy dealerStrategy,
			NextCheckStepStrategy nextCheckStepStrategy) {
		return new CheckStep(stepName, state, CHECK_TYPE_PERSONAL, dealerStrategy, nextCheckStepStrategy);
	}

	/**
	 * ��ȡĬ�ϵĴ�����
	 * ָ�������˲���
	 * Ĭ������Ϊ����ˡ�״̬Ϊ������ˡ��������Ϊ�����ˣ�Ĭ�ϵ���һ������
	 * @param dealerStrategy
	 * @return
	 */
	public static Step getDefaultCheckStep(DealerStrategy dealerStrategy) {
		return new CheckStep(STEP_CHECK, STATE_CHECK, CHECK_TYPE_PERSONAL, dealerStrategy,
				new DefaultNextCheckStepStrategy());
	}
	
	/**
	 * ��ȡĬ�ϵĴ�����
	 * Ĭ������Ϊ����ˡ�״̬Ϊ������ˡ��������Ϊ�����ˣ�Ĭ�ϵ���һ�����ԡ�������Ϊ��
	 * @param dealerStrategy
	 * @return
	 */
	public static Step getDefaultCheckStep() {
		return new CheckStep(STEP_CHECK, STATE_CHECK, CHECK_TYPE_PERSONAL, null, new DefaultNextCheckStepStrategy());
	}

	/**
	 * ��ȡĬ�ϵĲ������ӱ�
	 * @param stepName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getDefaultMap(String stepName) throws Exception {

		Map<String, String> map = new LinkedHashMap<String, String>();

		switch (stepName) {
		case STEP_CREAT:
			map.put(STEP_CREAT_CREAT, STEP_MODIFY);
			break;
		case STEP_MODIFY:
			map.put(STEP_MODIFY_MODIFY, STEP_MODIFY);
			map.put(STEP_MODIFY_SUBMIT, STEP_CHECK);
			break;
		case STEP_CHECK:
			map.put(STEP_CHECK_PAST, STEP_PAY);
			map.put(STEP_CHECK_BACK, STEP_MODIFY);
			map.put(STEP_CHECK_REFUSE, STEP_END);
			map.put(null, STEP_PAY);
			break;
		case STEP_END:
			map.put(null, null);
			break;
		case STEP_PAY:
			map.put(STEP_PAY_PAY, STEP_FINISH);
			break;
		case STEP_FINISH:
			map.put(null, null);
			break;
		default:
			throw new Exception("parameter 'stepName' invalid");
		}

		return map;
	}

	/**
	 * ָ�����ֲ������Ի�ȡĬ�ϵĲ���
	 * @param stepName
	 * @return
	 * @throws Exception
	 */
	public static Step getDefaultStep(String stepName) throws Exception {
		NextStepStrategy nextStepStrategy = new DefaultNextStepStrategy();
		return getDefaultStep(stepName, null, nextStepStrategy);
	}

	/**
	 * ָ�����ֲ������ʹ����˲����Ի�ȡĬ�ϵĲ���
	 * @param stepName
	 * @return
	 * @throws Exception
	 */
	public static Step getDefaultStep(String stepName, DealerStrategy dealerStrategy) throws Exception {
		NextStepStrategy nextStepStrategy = new DefaultNextStepStrategy();
		return getDefaultStep(stepName, dealerStrategy, nextStepStrategy);
	}

	/**
	 * ָ�����ֲ������ʹ����˲��Ժ���һ�������Ի�ȡĬ�ϵĲ���
	 * @param stepName
	 * @param dealerStrategy
	 * @param nextStepStrategy
	 * @return
	 * @throws Exception
	 */
	public static Step getDefaultStep(String stepName, DealerStrategy dealerStrategy, NextStepStrategy nextStepStrategy)
			throws Exception {

		String state = null;
		switch (stepName) {
		case STEP_CREAT:
			state = STATE_CREAT;
			break;
		case STEP_MODIFY:
			state = STATE_MODIFY;
			break;
		case STEP_CHECK:
			state = STATE_CHECK;
			break;
		case STEP_END:
			state = STATE_END;
			break;
		case STEP_PAY:
			state = STATE_PAY;
			break;
		case STEP_FINISH:
			state = STATE_FINISH;
			break;
		default:
			throw new Exception("parameter 'stepName' invalid");
		}
		return new Step(stepName, state, dealerStrategy, nextStepStrategy);
	}

}
