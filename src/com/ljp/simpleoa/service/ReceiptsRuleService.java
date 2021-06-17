package com.ljp.simpleoa.service;

import java.util.LinkedHashMap;

import com.ljp.simpleoa.Constant;
import com.ljp.simpleoa.model.Receipts;
import com.ljp.simpleoa.model.Worker;
import com.ljp.simpleoa.process.DealerStrategy;
import com.ljp.simpleoa.process.DefaultDealerStrategy;
import com.ljp.simpleoa.process.DefaultNextCheckStepStrategy;
import com.ljp.simpleoa.process.Process;
import com.ljp.simpleoa.process.ProcessBuilder;
import com.ljp.simpleoa.process.Step;
import com.ljp.simpleoa.process.StepUtils;

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

	private WorkerService workerService;
	private Process process;

	public ReceiptsRuleService(WorkerService workerService) {
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
		ProcessBuilder pb=new ProcessBuilder();
		MeDealerStrategy creatDealerStrategy = new MeDealerStrategy();
		DepartmentDealerStrategy departmentDealerStrategy = new DepartmentDealerStrategy();
		DefaultDealerStrategy generalManagerDealerStrategy = new DefaultDealerStrategy(
				workerService.getGeneralManager().getWorkerId());
		DefaultDealerStrategy financialerDealerStrategy = new DefaultDealerStrategy(
				workerService.getFinancialer().getWorkerId());
		try {
			// �������̣����˴�����ԣ�����Ĭ������
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_CREAT, creatDealerStrategy),
					StepUtils.getDefaultMap(StepUtils.STEP_CREAT));
			//�޸����̣����˴�����ԣ�������Ϊ����������һ�������̡��ύ��ΪConstant.STEP_CHECK1��������
			pb.addStep(
					StepUtils.getDefaultStep(StepUtils.STEP_MODIFY, creatDealerStrategy,
							new SubmitNextCheckStepStrategy()),
					StepUtils.creatNewDefaultModifyMap(Constant.STEP_CHECK1));
			//֧�����̣�����Ϊ�����ˣ�����Ĭ��
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_PAY, financialerDealerStrategy),
					StepUtils.getDefaultMap(StepUtils.STEP_PAY));
			//��ֹ���̣�������Ϊ�գ���һ��Ϊ��
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_END), StepUtils.getDefaultMap(StepUtils.STEP_END));
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_FINISH),
					StepUtils.getDefaultMap(StepUtils.STEP_FINISH));
			//�������̣���һ��Ϊ���󣬴������Ϊ��������ԣ�����Constant��STEP_CHECK1��STATE_CHECK1 ���ֺ�״̬����������ý���ж�
			pb.addStep(
					StepUtils.creatNewDefaultCheckStep(Constant.STEP_CHECK1, Constant.STATE_CHECK1,
							departmentDealerStrategy, new DepartmentNextCheckStepStrategy()),
					StepUtils.creatNewDefaultCheckMap(Constant.STEP_CHECK2));
			//�������̣���һ��ΪĬ�ϵ�֧��������Constant��STEP_CHECK2��STATE_CHECK2 ���ֺ�״̬��������Ϊ�ܾ���
			pb.addStep(
					StepUtils.creatNewDefaultCheckStep(Constant.STEP_CHECK2, Constant.STATE_CHECK2,
							generalManagerDealerStrategy, new DefaultNextCheckStepStrategy()),
					StepUtils.getDefaultMap(StepUtils.STEP_CHECK));
			process = pb.bulid();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
