package com.ljp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ljp.bean.Receipts;
import com.ljp.bean.ReceiptsDetails;
import com.ljp.bean.ReceiptsRecord;
import com.ljp.bean.Worker;
import com.ljp.common.Constant;
import com.ljp.mapperInterface.ReceiptsDetailsMapper;
import com.ljp.mapperInterface.ReceiptsMapper;
import com.ljp.mapperInterface.ReceiptsRecordMapper;
import com.ljp.process.Step;
import com.ljp.serviceInterface.ReceiptsServiceFace;
import com.ljp.serviceInterface.WorkerServiceFace;
import com.ljp.utils.PageInfo;
import com.ljp.utils.PageUtils;

@Service
public class ReceiptsService extends ReceiptsDaoService implements ReceiptsServiceFace{

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
	public ReceiptsService(WorkerServiceFace workerService, ReceiptsMapper receiptsMapper,
			ReceiptsDetailsMapper receiptsDetailsMapper, ReceiptsRecordMapper receiptsRecordMapper) {
		super(workerService, receiptsMapper, receiptsDetailsMapper, receiptsRecordMapper);
		this.receiptsRuleService = new ReceiptsRuleService(workerService);
	}

	/**
	 * ��鷽��
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	@Override
	public boolean check(ReceiptsRecord receiptsRecord, Worker user, String operation) {
		Receipts receipts = queryReceiptsByPK(receiptsRecord.getReceiptsId(), false);
		DealInfo dealInfo = getDealInfo(receipts, user, operation, null);
		if(dealInfo==null) {
			return false;
		}

		// 1������״̬
		receipts.setState(dealInfo.newState);
		receipts.setNextDealId(dealInfo.nextDealerId);
		// 3�����ӱ�����¼
		receiptsRecord.setDealResult(dealInfo.dealResult);
		receiptsRecord.setDealId(dealInfo.dealerId);
		receiptsRecord.setDealTime(dealInfo.dealDate);
		receiptsRecord.setDealType(dealInfo.dealType);

		dealDB(receipts, receiptsRecord);

		// ���������
		// DealInfo dealInfo1 = getDealInfo(receipts, user, operation,
		// receiptsRecord.getRepresention());
		// dealReceiptsAndDB(receipts, dealInfo1);
		return true;
	}

	/**
	 * ���󷽷�������鷽��һ��
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	@Override
	public boolean check2(ReceiptsRecord receiptsRecord, Worker user, String operation) {
		return check(receiptsRecord, user, operation);
	}

	/**
	 * ��������
	 * 
	 * @param receipts
	 * @param user
	 * @return
	 */
	@Override
	public boolean creat(Receipts receipts, Worker user) {
		receipts.setCreaterId(user.getWorkerId());
		DealInfo dealInfo = getDealInfo(receipts, user, Constant.STEP_CREAT_CREAT, null);
		if(dealInfo==null) {
			return false;
		}

		// 1�����ӱ�����
		receipts.setCreatTime(dealInfo.dealDate);
		receipts.setState(dealInfo.newState);
		receipts.setNextDealId(dealInfo.nextDealerId);
		addReceipts(receipts);
		// 2�����ӱ����굥
		List<ReceiptsDetails> items = receipts.getItems();
		addReceiptsDetails(receipts.getReceiptsId(), items);
		// 3�����ӱ�����¼
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
	 * ����״̬�����Ӵ�����¼�����ݿ����
	 * 
	 * @param receipts
	 * @param receiptsRecord
	 */
	@Override
	public void dealDB(Receipts receipts, ReceiptsRecord receiptsRecord) {
		updateReceiptsByPK(receipts, false);
		addReceiptsRecord(receiptsRecord);
	}

	/**
	 * �������ڴ���������Ϊû��receiptsID ��������Ҫ�ȴ������굥 �������������Ĵ�������
	 * 
	 * @param receipts
	 * @param dealInfo
	 */
	public void dealReceiptsAndDB(Receipts receipts, DealInfo dealInfo) {
		// 1�����µ���״̬
		receipts.setState(dealInfo.newState);
		receipts.setNextDealId(dealInfo.nextDealerId);
		// 3�����ӱ�����¼
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
	 * ��ȡ��������Ϣ
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

	@Override
	public List<Receipts> getReceiptsByCreaterIdAndPageInfo(Worker creater, PageInfo pageInfo, String toPage) {

		pageInfo.setRowCount(queryReciptesByCreaterIdCount(creater.getWorkerId()));

		PageUtils.setToPage(pageInfo, toPage);
		return queryReciptesByCreaterIdAndLimit(creater.getWorkerId(), pageInfo.getLimit(), pageInfo.getPageSize(),false);
	}

	@Override
	public List<Receipts> getReceiptsByDealerIdAndPageInfo(Worker dealer, PageInfo pageInfo, String toPage) {

		pageInfo.setRowCount(queryReciptesByDealerIdCount(dealer.getWorkerId()));

		PageUtils.setToPage(pageInfo, toPage);
		return queryReciptesByDealerIdAndLimit(dealer.getWorkerId(), pageInfo.getLimit(), pageInfo.getPageSize(),false);
	}

	/**
	 * ֧������������鷽��һ��
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	@Override
	public boolean pay(ReceiptsRecord receiptsRecord, Worker user, String operation) {
		return check(receiptsRecord, user, operation);
	}

	/**
	 * �ύ����
	 * 
	 * @param receiptsId
	 * @param user
	 * @return
	 */
	@Override
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
	 * ���·���
	 * 
	 * @param receipts
	 * @param user
	 * @return
	 */
	@Override
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