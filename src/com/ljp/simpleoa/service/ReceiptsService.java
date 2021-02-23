package com.ljp.simpleoa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ljp.simpleoa.Constant;
import com.ljp.simpleoa.mapper.ReceiptsDetailsMapper;
import com.ljp.simpleoa.mapper.ReceiptsMapper;
import com.ljp.simpleoa.mapper.ReceiptsRecordMapper;
import com.ljp.simpleoa.model.Receipts;
import com.ljp.simpleoa.model.ReceiptsDetails;
import com.ljp.simpleoa.model.ReceiptsRecord;
import com.ljp.simpleoa.model.Worker;
import com.ljp.simpleoa.process.Step;
import com.ljp.simpleoa.utils.PageInfo;

@Service
public class ReceiptsService extends ReceiptsDaoService {

	class DealInfo {
		public Date dealDate;
		public String dealType;
		public String dealResult;
		public String newState;
		public Integer nextDealerId;
		public Integer dealerId;
		public String represention;

		@Override
		public String toString() {
			return "DealInfo [dealDate=" + dealDate + ", dealType=" + dealType + ", dealResult=" + dealResult
					+ ", newState=" + newState + ", nextDealerId=" + nextDealerId + ", dealerId=" + dealerId
					+ ", represention=" + represention + "]";
		}

	}

	private ReceiptsRuleService receiptsRuleService;

	@Autowired
	public ReceiptsService(WorkerService workerService, ReceiptsMapper receiptsMapper,
			ReceiptsDetailsMapper receiptsDetailsMapper, ReceiptsRecordMapper receiptsRecordMapper) {
		super(workerService, receiptsMapper, receiptsDetailsMapper, receiptsRecordMapper);
		if(workerService!=null)
		this.receiptsRuleService = new ReceiptsRuleService(workerService);
	}

	/**
	 * 审查方法
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	public boolean check(ReceiptsRecord receiptsRecord, Worker user, String operation) {
		Receipts receipts = queryReceiptsByPK(receiptsRecord.getReceiptsId(), false);
		DealInfo dealInfo = getDealInfo(receipts, user, operation, null);
		if(dealInfo==null) {
			return false;
		}

		// 1、更改状态
		receipts.setState(dealInfo.newState);
		receipts.setNextDealId(dealInfo.nextDealerId);
		// 3、添加报销记录
		receiptsRecord.setDealResult(dealInfo.dealResult);
		receiptsRecord.setDealId(dealInfo.dealerId);
		receiptsRecord.setDealTime(dealInfo.dealDate);
		receiptsRecord.setDealType(dealInfo.dealType);

		dealDB(receipts, receiptsRecord);

		// 这样更简洁
		// DealInfo dealInfo1 = getDealInfo(receipts, user, operation,
		// receiptsRecord.getRepresention());
		// dealReceiptsAndDB(receipts, dealInfo1);
		return true;
	}

	/**
	 * 复审方法，和审查方法一样
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	public boolean check2(ReceiptsRecord receiptsRecord, Worker user, String operation) {
		return check(receiptsRecord, user, operation);
	}

	/**
	 * 创建方法
	 * 
	 * @param receipts
	 * @param user
	 * @return
	 */
	public boolean creat(Receipts receipts, Worker user) {
		receipts.setCreaterId(user.getWorkerId());
		DealInfo dealInfo = getDealInfo(receipts, user, Constant.STEP_CREAT_CREAT, null);
		if(dealInfo==null) {
			return false;
		}

		// 1、添加报销单
		receipts.setCreatTime(dealInfo.dealDate);
		receipts.setState(dealInfo.newState);
		receipts.setNextDealId(dealInfo.nextDealerId);
		addReceipts(receipts);
		// 2、添加保销详单
		List<ReceiptsDetails> items = receipts.getItems();
		addReceiptsDetails(receipts.getReceiptsId(), items);
		// 3、添加报销记录
		ReceiptsRecord receiptsRecord = new ReceiptsRecord();
		receiptsRecord.setReceiptsId(receipts.getReceiptsId());
		receiptsRecord.setDealResult(dealInfo.dealResult);
		receiptsRecord.setDealId(dealInfo.dealerId);
		receiptsRecord.setDealTime(dealInfo.dealDate);
		receiptsRecord.setDealType(dealInfo.dealType);
		receiptsRecord.setRepresention(dealInfo.represention);
		addReceiptsRecord(receiptsRecord);
		return true;
	}

	/**
	 * 更新状态和添加处理记录的数据库操作
	 * 
	 * @param receipts
	 * @param receiptsRecord
	 */
	public void dealDB(Receipts receipts, ReceiptsRecord receiptsRecord) {
		updateReceiptsByPK(receipts, false);
		addReceiptsRecord(receiptsRecord);
	}

	/**
	 * 不适用于创建流程因为没有receiptsID 更新流程要先处理好详单 检查流程有特殊的处理方法
	 * 
	 * @param receipts
	 * @param dealInfo
	 */
	public void dealReceiptsAndDB(Receipts receipts, DealInfo dealInfo) {
		// 1、更新单据状态
		receipts.setState(dealInfo.newState);
		receipts.setNextDealId(dealInfo.nextDealerId);
		// 3、添加报销记录
		ReceiptsRecord receiptsRecord = new ReceiptsRecord();
		receiptsRecord.setReceiptsId(receipts.getReceiptsId());
		receiptsRecord.setDealResult(dealInfo.dealResult);
		receiptsRecord.setDealId(dealInfo.dealerId);
		receiptsRecord.setDealTime(dealInfo.dealDate);
		receiptsRecord.setDealType(dealInfo.dealType);
		receiptsRecord.setRepresention(dealInfo.represention);
		dealDB(receipts, receiptsRecord);
	}

	/**
	 * 获取单处理信息
	 * 
	 * @param receipts
	 * @param user
	 * @param operation
	 */
	public DealInfo getDealInfo(Receipts receipts, Worker user, String operation, String represention) {
		DealInfo dealInfo = new DealInfo();
		Step thisStep = receiptsRuleService.getProcess().thisStepByState(receipts.getState());		
		if(thisStep==null||!user.getWorkerId().equals((Integer) thisStep.getDealer(receipts))){
			return null;
		}
		Step nextStep = receiptsRuleService.getProcess().nextStepByState(receipts.getState(), operation, receipts);
		if(nextStep==null) {
			return null;
		}

		dealInfo.dealDate = new Date();
		dealInfo.dealType = thisStep.getName();
		dealInfo.dealResult = operation;
		dealInfo.newState = nextStep.getState();
		dealInfo.nextDealerId = (Integer) nextStep.getDealer(receipts);
		dealInfo.dealerId = user.getWorkerId();
		dealInfo.represention = represention;
		return dealInfo;
	}

	public List<Receipts> getReceiptsByCreaterIdAndPageInfo(Worker creater, PageInfo pageInfo, String toPage) {

		pageInfo.setRowCount(queryReciptesByCreaterIdCount(creater.getWorkerId()));

		PageUtils.setToPage(pageInfo, toPage);
		return queryReciptesByCreaterIdAndLimit(creater.getWorkerId(), pageInfo.getLimit(), pageInfo.getPageSize(),false);
	}

	public List<Receipts> getReceiptsByDealerIdAndPageInfo(Worker dealer, PageInfo pageInfo, String toPage) {

		pageInfo.setRowCount(queryReciptesByDealerIdCount(dealer.getWorkerId()));

		PageUtils.setToPage(pageInfo, toPage);
		return queryReciptesByDealerIdAndLimit(dealer.getWorkerId(), pageInfo.getLimit(), pageInfo.getPageSize(),false);
	}

	/**
	 * 支付方法，和审查方法一样
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	public boolean pay(ReceiptsRecord receiptsRecord, Worker user, String operation) {
		return check(receiptsRecord, user, operation);
	}

	/**
	 * 提交方法
	 * 
	 * @param receiptsId
	 * @param user
	 * @return
	 */
	public boolean submit(Integer receiptsId, Worker user) {
		Receipts receipts = queryReceiptsByPK(receiptsId, false);
		DealInfo dealInfo = getDealInfo(receipts, user, Constant.STEP_MODIFY_SUBMIT, null);
		if(dealInfo==null) {
			return false;
		}
		dealReceiptsAndDB(receipts, dealInfo);
		return true;

	}

	/**
	 * 更新方法
	 * 
	 * @param receipts
	 * @param user
	 * @return
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public boolean update(Receipts receipts, Worker user) {
		DealInfo dealInfo = getDealInfo(receipts, user, Constant.STEP_MODIFY_MODIFY, null);
		if(dealInfo==null) {
			return false;
		}
		updateReceiptsDetailsByReceiptsId(receipts.getReceiptsId(), receipts.getItems());

		dealReceiptsAndDB(receipts, dealInfo);
		return true;
	}

}
