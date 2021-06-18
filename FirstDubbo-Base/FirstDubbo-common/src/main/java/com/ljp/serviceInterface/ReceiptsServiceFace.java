package com.ljp.serviceInterface;

import java.util.List;

import com.ljp.bean.Receipts;
import com.ljp.bean.ReceiptsRecord;
import com.ljp.bean.Worker;
import com.ljp.utils.PageInfo;

public interface ReceiptsServiceFace extends ReceiptsDaoServiceFace{

	/**
	 * ��鷽��?
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	boolean check(ReceiptsRecord receiptsRecord, Worker user, String operation);

	/**
	 * ���󷽷�������鷽��һ��?
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	boolean check2(ReceiptsRecord receiptsRecord, Worker user, String operation);

	/**
	 * ��������
	 * 
	 * @param receipts
	 * @param user
	 * @return
	 */
	boolean creat(Receipts receipts, Worker user);

	/**
	 * ����״̬����Ӵ����¼�����ݿ����?
	 * 
	 * @param receipts
	 * @param receiptsRecord
	 */
	void dealDB(Receipts receipts, ReceiptsRecord receiptsRecord);

	List<Receipts> getReceiptsByCreaterIdAndPageInfo(Worker creater, PageInfo pageInfo, String toPage);

	List<Receipts> getReceiptsByDealerIdAndPageInfo(Worker dealer, PageInfo pageInfo, String toPage);

	/**
	 * ֧������������鷽��һ��?
	 * 
	 * @param receiptsRecord
	 * @param user
	 * @param operation
	 * @return
	 */
	boolean pay(ReceiptsRecord receiptsRecord, Worker user, String operation);

	/**
	 * �ύ����
	 * 
	 * @param receiptsId
	 * @param user
	 * @return
	 */
	boolean submit(Integer receiptsId, Worker user);

	/**
	 * ���·���
	 * 
	 * @param receipts
	 * @param user
	 * @return
	 */
	boolean update(Receipts receipts, Worker user);

}