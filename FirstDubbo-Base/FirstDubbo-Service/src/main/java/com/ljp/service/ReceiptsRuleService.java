package com.ljp.service;

import java.util.LinkedHashMap;

import com.ljp.bean.Receipts;
import com.ljp.bean.Worker;
import com.ljp.common.Constant;
import com.ljp.process.DealerStrategy;
import com.ljp.process.DefaultDealerStrategy;
import com.ljp.process.DefaultNextCheckStepStrategy;
import com.ljp.process.Process;
import com.ljp.process.Step;
import com.ljp.process.StepUtils;
import com.ljp.serviceInterface.WorkerServiceFace;

public class ReceiptsRuleService {

	/**
	 * ���������˲��ԣ�ͨ��Ա�����Ҳ�����
	 * 
	 * @author LiJunPeng
	 *
	 */
	class DepartmentDealerStrategy implements DealerStrategy {

		@Override
		public Object getDealer(Object arg) {
			if (arg instanceof Receipts) {
				Receipts receipts = (Receipts) arg;
				System.out.println("receipts.getCreaterId()" + receipts);
				Worker departmentManager = workerService.getDepartmentManager(receipts.getCreaterId());
				System.out.println("departmentManager" + departmentManager);
				if (departmentManager == null)
					return null;
				return departmentManager.getWorkerId();
			}
			return null;
		}

	}

	/**
	 * ������Ϊ�����˱��˲���,Ӧ�����޸�����
	 * 
	 * @author LiJunPeng
	 *
	 */
	class MeDealerStrategy implements DealerStrategy {

		@Override
		public Object getDealer(Object arg) {
			if (arg instanceof Receipts) {
				Receipts receipts = (Receipts) arg;
				return receipts.getCreaterId();
			}
			return null;
		}

	}

	/**
	 * ��������˺�ĸ���������������ݵ��ݽ���ж�
	 * 
	 * @author LiJunPeng
	 *
	 */
	class DepartmentNextCheckStepStrategy extends DefaultNextCheckStepStrategy {

		@Override
		public boolean isNeedToCheck(Object arg) {
			if (arg instanceof Receipts) {
				Receipts receipts = (Receipts) arg;
				return receipts.getTotalmoney() > Constant.RECHECK_MONEY;
			}
			return false;
		}

	}

	/**
	 * �ύ�󲿾�����������Ҫ���г���ֱ���ܾ�����
	 * 
	 * @author LiJunPeng
	 *
	 */
	class SubmitNextCheckStepStrategy extends DefaultNextCheckStepStrategy {

		@Override
		public Step getNextStep(String operation, LinkedHashMap<String, Step> nextStepMap, Object arg) {

			Step result = super.getNextStep(operation, nextStepMap, arg);
			if (result.getDealer(arg) == null) {
				return result.getNextStep(arg);
			}
			return result;
		}

	}

	private WorkerServiceFace workerService;
	private Process process;

	public ReceiptsRuleService(WorkerServiceFace workerService) {
		this.workerService = workerService;
		init();
	}

	public Process getProcess() {
		return process;
	}

	/**
	 * ��ʼ�����̴���
	 */
	public void init() {
		process = new Process();
		MeDealerStrategy creatDealerStrategy = new MeDealerStrategy();
		DepartmentDealerStrategy departmentDealerStrategy = new DepartmentDealerStrategy();
		DefaultDealerStrategy generalManagerDealerStrategy = new DefaultDealerStrategy(
				workerService.getGeneralManager().getWorkerId());
		DefaultDealerStrategy financialerDealerStrategy = new DefaultDealerStrategy(
				workerService.getFinancialer().getWorkerId());
		try {
			// �������̣����˴�����ԣ�����Ĭ������
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_CREAT, creatDealerStrategy),
					StepUtils.getDefaultMap(StepUtils.STEP_CREAT));
			//�޸����̣����˴�����ԣ�������Ϊ����������һ�������̡��ύ��ΪConstant.STEP_CHECK1��������
			process.addStep(
					StepUtils.getDefaultStep(StepUtils.STEP_MODIFY, creatDealerStrategy,
							new SubmitNextCheckStepStrategy()),
					StepUtils.creatNewDefaultModifyMap(Constant.STEP_CHECK1));
			//֧�����̣�����Ϊ�����ˣ�����Ĭ��
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_PAY, financialerDealerStrategy),
					StepUtils.getDefaultMap(StepUtils.STEP_PAY));
			//��ֹ���̣�������Ϊ�գ���һ��Ϊ��
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_END), StepUtils.getDefaultMap(StepUtils.STEP_END));
			process.addStep(StepUtils.getDefaultStep(StepUtils.STEP_FINISH),
					StepUtils.getDefaultMap(StepUtils.STEP_FINISH));
			//�������̣���һ��Ϊ���󣬴������Ϊ��������ԣ�����Constant��STEP_CHECK1��STATE_CHECK1 ���ֺ�״̬����������ý���ж�
			process.addStep(
					StepUtils.creatNewDefaultCheckStep(Constant.STEP_CHECK1, Constant.STATE_CHECK1,
							departmentDealerStrategy, new DepartmentNextCheckStepStrategy()),
					StepUtils.creatNewDefaultCheckMap(Constant.STEP_CHECK2));
			//�������̣���һ��ΪĬ�ϵ�֧��������Constant��STEP_CHECK2��STATE_CHECK2 ���ֺ�״̬��������Ϊ�ܾ���
			process.addStep(
					StepUtils.creatNewDefaultCheckStep(Constant.STEP_CHECK2, Constant.STATE_CHECK2,
							generalManagerDealerStrategy, new DefaultNextCheckStepStrategy()),
					StepUtils.getDefaultMap(StepUtils.STEP_CHECK));
			process.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
