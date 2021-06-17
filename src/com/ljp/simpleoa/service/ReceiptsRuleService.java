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
	 * 部经理处理人策略，通过员工查找部经理
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
	 * 处理人为创建人本人策略,应用在修改流程
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
	 * 部经理审核后的复审标题条件，根据单据金额判断
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
	 * 提交后部经理不存在则不需要进行初审，直接总经理审
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
	 * 初始化流程处理
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
			// 创建流程：本人处理策略，其他默认流程
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_CREAT, creatDealerStrategy),
					StepUtils.getDefaultMap(StepUtils.STEP_CREAT));
			//修改流程，本人处理策略，处理人为空则跳过下一处理流程、提交后为Constant.STEP_CHECK1初审流程
			pb.addStep(
					StepUtils.getDefaultStep(StepUtils.STEP_MODIFY, creatDealerStrategy,
							new SubmitNextCheckStepStrategy()),
					StepUtils.creatNewDefaultModifyMap(Constant.STEP_CHECK1));
			//支付流程，财务为处理人，其他默认
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_PAY, financialerDealerStrategy),
					StepUtils.getDefaultMap(StepUtils.STEP_PAY));
			//终止流程，处理人为空，下一步为空
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_END), StepUtils.getDefaultMap(StepUtils.STEP_END));
			pb.addStep(StepUtils.getDefaultStep(StepUtils.STEP_FINISH),
					StepUtils.getDefaultMap(StepUtils.STEP_FINISH));
			//初审流程，下一部为复审，处理策略为部经理策略，采用Constant的STEP_CHECK1，STATE_CHECK1 名字和状态，复审策略用金额判断
			pb.addStep(
					StepUtils.creatNewDefaultCheckStep(Constant.STEP_CHECK1, Constant.STATE_CHECK1,
							departmentDealerStrategy, new DepartmentNextCheckStepStrategy()),
					StepUtils.creatNewDefaultCheckMap(Constant.STEP_CHECK2));
			//复审流程，下一步为默认的支付，采用Constant的STEP_CHECK2，STATE_CHECK2 名字和状态，处理人为总经理
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
