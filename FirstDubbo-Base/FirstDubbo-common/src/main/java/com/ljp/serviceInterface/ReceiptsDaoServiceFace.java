package com.ljp.serviceInterface;

import java.util.List;

import com.ljp.bean.Receipts;
import com.ljp.bean.ReceiptsDetails;
import com.ljp.bean.ReceiptsRecord;

public interface ReceiptsDaoServiceFace {

	boolean removeReceipts(String receiptsIds);

	int addReceipts(Receipts receipts);

	int addReceiptsDetails(Integer receiptsId, List<ReceiptsDetails> items);

	int addReceiptsDetails(ReceiptsDetails receiptsDetails);

	int addReceiptsRecord(ReceiptsRecord receiptsRecord);

	List<Receipts> queryAll(boolean append);

	boolean queryReceiptsAppend(Receipts receipts);

	List<Receipts> queryReceiptsByCreaterId(Integer createrId, boolean append);

	List<Receipts> queryReceiptsByDealerId(Integer dealerId, boolean append);

	Receipts queryReceiptsByPK(int receiptsId, boolean append);

	/**
	 * ���ݱ�����id���Ҽ�¼
	 * 
	 * @param receiptsId
	 * @return
	 */
	List<ReceiptsDetails> queryReceiptsDetailsByReceiptsId(Integer receiptsId);

	List<ReceiptsRecord> queryReceiptsRecordReceiptsId(Integer receiptsId);

	List<Receipts> queryReciptesByCreaterIdAndLimit(Integer createrId, int offSet, int pageSize, boolean append);

	int queryReciptesByCreaterIdCount(Integer createrId);

	List<Receipts> queryReciptesByDealerIdAndLimit(Integer dealerId, int offSet, int pageSize, boolean append);

	int queryReciptesByDealerIdCount(Integer dealerId);

	int updateReceiptsByPK(Receipts receipts, boolean selective);

	boolean updateReceiptsDetailsByReceiptsId(Integer receiptsId, List<ReceiptsDetails> newItems);

}