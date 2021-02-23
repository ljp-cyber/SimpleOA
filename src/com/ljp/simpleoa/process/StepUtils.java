package com.ljp.simpleoa.process;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 此类可以获取一些默认通用的流程 一些流程的处理人策略为空或不符合要求，可以调用Step实例的setDealerStrategy()方法设置新策略
 * 不是设置处理人策略，Step的处理人 返回类型为Object
 * 默认流程为：
 * 1、创建
 * 创建：修改
 * 2、修改
 * 修改：修改
 * 提交：审核
 * 3、审核
 * 通过：打款
 * 拒绝：终止
 * 打回：修改
 * 4、打款
 * 打款：完成
 * 5、完成
 * 没有
 * 6、终止
 * 没有
 * 
 * @author LiJunPeng
 *
 */
public class StepUtils {

	public static final String STEP_CREAT = "创建";
	public static final String STEP_MODIFY = "修改";
	public static final String STEP_CHECK = "审核";
	public static final String STEP_PAY = "打款";
	public static final String STEP_FINISH = "完成";
	public static final String STEP_END = "终止";
	
	public static final String STEP_CREAT_CREAT="创建";
	public static final String STEP_MODIFY_MODIFY = "修改";
	public static final String STEP_MODIFY_SUBMIT = "提交";
	public static final String STEP_CHECK_PAST = "通过";
	public static final String STEP_CHECK_BACK = "打回";
	public static final String STEP_CHECK_REFUSE = "拒绝";
	public static final String STEP_PAY_PAY = "打款";

	public static final String CHECK_TYPE_PERSONAL = "个人";
	public static final String CHECK_TYPE_UNITE = "会审";

	public static final String STATE_CREAT = null;
	public static final String STATE_MODIFY = "待提交";
	public static final String STATE_CHECK = "待审核";
	public static final String STATE_PAY = "待支付";
	public static final String STATE_FINISH = "已打款";
	public static final String STATE_END = "已终结";

	/**
	 * 创建默认的审查步骤连接表，指定‘通过’操作后步骤名
	 * @param nextOfPass ‘通过’操作后步骤名
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
	 * 创建默认的提交步骤连接表，指定‘提交’操作后步骤名
	 * @param stepOfSubmit 通过后步骤名
	 * @return
	 */
	public static Map<String, String> creatNewDefaultModifyMap(String stepOfSubmit) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(STEP_MODIFY_MODIFY, STEP_MODIFY);
		map.put(STEP_MODIFY_SUBMIT, stepOfSubmit);
		return map;
	}

	/**
	 * 更换的‘通过’操作后步骤
	 * @param step
	 * @param newStep
	 */
	public static void changeNextOfPass(Step step, Step newStep) {
		step.getNextStepMap().put(STEP_CHECK_PAST, newStep);
	}

	/**
	 * 创建默认的审查步骤
	 * 指定步骤名，状态名、
	 * 默认：审查类型为‘个人’、处理人策略为null、默认的下一步处理策略
	 * @param stepName
	 * @param state
	 * @return
	 */
	public static Step creatNewDefaultCheckStep(String stepName, String state) {
		return new CheckStep(stepName, state, CHECK_TYPE_PERSONAL, null, new DefaultNextCheckStepStrategy());
	}

	/**
	 * 创建默认的审查步骤
	 * 指定步骤名，状态名、处理人策略、和下一步策略
	 * 审查类型为‘个人’
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
	 * 获取默认的处理步骤
	 * 指定处理人策略
	 * 默认名字为：审核、状态为：待审核、审查类型为：个人，默认的下一步策略
	 * @param dealerStrategy
	 * @return
	 */
	public static Step getDefaultCheckStep(DealerStrategy dealerStrategy) {
		return new CheckStep(STEP_CHECK, STATE_CHECK, CHECK_TYPE_PERSONAL, dealerStrategy,
				new DefaultNextCheckStepStrategy());
	}
	
	/**
	 * 获取默认的处理步骤
	 * 默认名字为：审核、状态为：待审核、审查类型为：个人，默认的下一步策略、处理人为空
	 * @param dealerStrategy
	 * @return
	 */
	public static Step getDefaultCheckStep() {
		return new CheckStep(STEP_CHECK, STATE_CHECK, CHECK_TYPE_PERSONAL, null, new DefaultNextCheckStepStrategy());
	}

	/**
	 * 获取默认的操作连接表
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
	 * 指定名字步骤名以获取默认的步骤
	 * @param stepName
	 * @return
	 * @throws Exception
	 */
	public static Step getDefaultStep(String stepName) throws Exception {
		NextStepStrategy nextStepStrategy = new DefaultNextStepStrategy();
		return getDefaultStep(stepName, null, nextStepStrategy);
	}

	/**
	 * 指定名字步骤名和处理人策略以获取默认的步骤
	 * @param stepName
	 * @return
	 * @throws Exception
	 */
	public static Step getDefaultStep(String stepName, DealerStrategy dealerStrategy) throws Exception {
		NextStepStrategy nextStepStrategy = new DefaultNextStepStrategy();
		return getDefaultStep(stepName, dealerStrategy, nextStepStrategy);
	}

	/**
	 * 指定名字步骤名和处理人策略和下一步策略以获取默认的步骤
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
