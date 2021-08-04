package com.ljp.simpleoa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ljp.simpleoa.mapper.ReceiptsDetailsMapper;
import com.ljp.simpleoa.mapper.ReceiptsMapper;
import com.ljp.simpleoa.mapper.ReceiptsRecordMapper;
import com.ljp.simpleoa.model.Receipts;
import com.ljp.simpleoa.model.ReceiptsDetails;
import com.ljp.simpleoa.model.ReceiptsDetailsExample;
import com.ljp.simpleoa.model.ReceiptsExample;
import com.ljp.simpleoa.model.ReceiptsRecord;
import com.ljp.simpleoa.model.ReceiptsRecordExample;

public class ReceiptsDaoService {

	protected WorkerService workerService;
	protected ReceiptsMapper receiptsMapper;
	protected ReceiptsDetailsMapper receiptsDetailsMapper;
	protected ReceiptsRecordMapper receiptsRecordMapper;

	public ReceiptsDaoService(WorkerService workerService, ReceiptsMapper receiptsMapper,
			ReceiptsDetailsMapper receiptsDetailsMapper, ReceiptsRecordMapper receiptsRecordMapper) {

		this.workerService = workerService;
		this.receiptsMapper = receiptsMapper;
		this.receiptsDetailsMapper = receiptsDetailsMapper;
		this.receiptsRecordMapper = receiptsRecordMapper;
	}
	
	/**
	 * 删除报销单要同时删除报销单详情，开启事务
	 * 可以用sql语句优化
	 * @param receiptsIds
	 * @return
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public boolean removeReceipts(String receiptsIds) {
		String[] strings = receiptsIds.split(",");
		List<Integer> integers=new ArrayList<Integer>();
		for (int i = 0; i < strings.length; i++) {
			String string = strings[i];
			integers.add(Integer.parseInt(string));
		}
		System.out.println(strings);
		System.out.println(integers);
		ReceiptsDetailsExample example2=new ReceiptsDetailsExample();
		ReceiptsDetailsExample.Criteria criteria2 = example2.createCriteria();
		criteria2.andReceiptsIdIn(integers);
		receiptsDetailsMapper.deleteByExample(example2);
		
		ReceiptsRecordExample example3=new ReceiptsRecordExample();
		ReceiptsRecordExample.Criteria criteria3 = example3.createCriteria();
		criteria3.andReceiptsIdIn(integers);
		receiptsRecordMapper.deleteByExample(example3);
		
		ReceiptsExample example1=new ReceiptsExample();
		ReceiptsExample.Criteria criteria1 = example1.createCriteria();
		criteria1.andReceiptsIdIn(integers);
		receiptsMapper.deleteByExample(example1);
		return true;
	}

	public int addReceipts(Receipts receipts) {
		int count = receiptsMapper.insert(receipts);
		return count;
	}

	public int addReceiptsDetails(Integer receiptsId, List<ReceiptsDetails> items) {
		int count = 0;
		for (ReceiptsDetails receiptsDetails : items) {
			receiptsDetails.setReceiptsId(receiptsId);
			int countDettails = addReceiptsDetails(receiptsDetails);
			count += countDettails;
		}
		return count;
	}

	public int addReceiptsDetails(ReceiptsDetails receiptsDetails) {
		int count = receiptsDetailsMapper.insert(receiptsDetails);
		return count;
	}

	public int addReceiptsRecord(ReceiptsRecord receiptsRecord) {
		int count = receiptsRecordMapper.insert(receiptsRecord);
		return count;
	}

	public List<Receipts> queryAll(boolean append) {
		List<Receipts> list = receiptsMapper.selectByExample(null);
		if (append) {
			for (Receipts receipts : list) {
				queryReceiptsAppend(receipts);
			}
		}
		return list;
	}

	public boolean queryReceiptsAppend(Receipts receipts) {
		// 设置下一处理人
		receipts.setDealer(workerService.queryByPrimaryKey(receipts.getNextDealId()));
		// 设置创建人
		receipts.setCreater(workerService.queryByPrimaryKey(receipts.getCreaterId()));
		// 设置报销详单
		List<ReceiptsDetails> list = queryReceiptsDetailsByReceiptsId(receipts.getReceiptsId());
		receipts.setItems(list);
		// 设置报销流程
		List<ReceiptsRecord> list2 = queryReceiptsRecordReceiptsId(receipts.getReceiptsId());
		receipts.setRecords(list2);
		return true;
	}

	public List<Receipts> queryReceiptsByCreaterId(Integer createrId, boolean append) {
		ReceiptsExample example = new ReceiptsExample();
		ReceiptsExample.Criteria criteria = example.createCriteria();
		criteria.andCreaterIdEqualTo(createrId);
		List<Receipts> list = receiptsMapper.selectByExampleAndLeftJoinWorker(example);
		if (append) {
			for (Receipts receipts : list) {
				queryReceiptsAppend(receipts);
			}
		}
		return list;
	}

	public List<Receipts> queryReceiptsByDealerId(Integer dealerId, boolean append) {
		ReceiptsExample example = new ReceiptsExample();
		ReceiptsExample.Criteria criteria = example.createCriteria();
		criteria.andNextDealIdEqualTo(dealerId);
		List<Receipts> list = receiptsMapper.selectByExampleAndLeftJoinWorker(example);
		if (append) {
			for (Receipts receipts : list) {
				queryReceiptsAppend(receipts);
			}
		}
		return list;
	}

	public Receipts queryReceiptsByPK(int receiptsId, boolean append) {
		Receipts receipts = receiptsMapper.selectByPrimaryKey(receiptsId);
		if (append) {
			queryReceiptsAppend(receipts);
		}
		return receipts;
	}

	/**
	 * 根据报销单id查找记录
	 * 
	 * @param receiptsId
	 * @return
	 */
	public List<ReceiptsDetails> queryReceiptsDetailsByReceiptsId(Integer receiptsId) {
		ReceiptsDetailsExample receiptsDetailsExample = new ReceiptsDetailsExample();
		ReceiptsDetailsExample.Criteria receiptsDetailsCriteria = receiptsDetailsExample.createCriteria();
		receiptsDetailsCriteria.andReceiptsIdEqualTo(receiptsId);
		List<ReceiptsDetails> list = receiptsDetailsMapper.selectByExample(receiptsDetailsExample);
		return list;
	}

	public List<ReceiptsRecord> queryReceiptsRecordReceiptsId(Integer receiptsId) {
		ReceiptsRecordExample receiptsRecordExample = new ReceiptsRecordExample();
		ReceiptsRecordExample.Criteria receiptsRecordCriteria = receiptsRecordExample.createCriteria();
		receiptsRecordCriteria.andReceiptsIdEqualTo(receiptsId);
		List<ReceiptsRecord> list2 = receiptsRecordMapper.selectByExampleAndLeftJoinWorker(receiptsRecordExample);
		return list2;
	}

	public List<Receipts> queryReciptesByCreaterIdAndLimit(Integer createrId, int offSet, int pageSize,
			boolean append) {
		ReceiptsExample example = new ReceiptsExample();
		ReceiptsExample.Criteria criteria = example.createCriteria();
		criteria.andCreaterIdEqualTo(createrId);
		example.setOffset(offSet);
		example.setLimit(pageSize);
		List<Receipts> list = receiptsMapper.selectByExampleAndLeftJoinWorker(example);
		if (append) {
			for (Receipts receipts : list) {
				queryReceiptsAppend(receipts);
			}
		}
		return list;
	}

	public int queryReciptesByCreaterIdCount(Integer createrId) {
		ReceiptsExample example = new ReceiptsExample();
		ReceiptsExample.Criteria criteria = example.createCriteria();
		criteria.andCreaterIdEqualTo(createrId);
		return (int) receiptsMapper.countByExample(example);
	}

	public List<Receipts> queryReciptesByDealerIdAndLimit(Integer dealerId, int offSet, int pageSize, boolean append) {
		ReceiptsExample example = new ReceiptsExample();
		ReceiptsExample.Criteria criteria = example.createCriteria();
		criteria.andNextDealIdEqualTo(dealerId);
		example.setOffset(offSet);
		example.setLimit(pageSize);
		List<Receipts> list = receiptsMapper.selectByExampleAndLeftJoinWorker(example);
		if (append) {
			for (Receipts receipts : list) {
				queryReceiptsAppend(receipts);
			}
		}
		return list;
	}

	public int queryReciptesByDealerIdCount(Integer dealerId) {
		ReceiptsExample example = new ReceiptsExample();
		ReceiptsExample.Criteria criteria = example.createCriteria();
		criteria.andNextDealIdEqualTo(dealerId);
		return (int) receiptsMapper.countByExample(example);
	}

	public int updateReceiptsByPK(Receipts receipts, boolean selective) {
		int count = 0;
		if (!selective) {
			count = receiptsMapper.updateByPrimaryKey(receipts);
		} else {
			count = receiptsMapper.updateByPrimaryKeySelective(receipts);
		}
		return count;
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public boolean updateReceiptsDetailsByReceiptsId(Integer receiptsId, List<ReceiptsDetails> newItems) {
		List<ReceiptsDetails> oldItems = queryReceiptsDetailsByReceiptsId(receiptsId);
		System.out.println("*****oldItems******" + oldItems);
		System.out.println("*****newItems******" + newItems);
		for (ReceiptsDetails receiptsDetails : newItems) {
			int countDettails = 0;
			if (receiptsDetails.getReceiptsDetailsId() == null) {
				receiptsDetails.setReceiptsId(receiptsId);

				countDettails = receiptsDetailsMapper.insert(receiptsDetails);
				System.out.println("插入条数：" + countDettails);
				if (countDettails == 0) {
					return false;
				}
			} else {
				countDettails = receiptsDetailsMapper.updateByPrimaryKey(receiptsDetails);
				System.out.println("更新条数：" + countDettails);
				for (int i = 0; i < oldItems.size(); i++) {
					ReceiptsDetails receiptsDetails2 = oldItems.get(i);

					System.out.println("receiptsDetails2:" + receiptsDetails2.getReceiptsDetailsId().intValue());
					System.out.println("receiptsDetails:" + receiptsDetails.getReceiptsDetailsId().intValue());

					if (receiptsDetails2.getReceiptsDetailsId().intValue() == receiptsDetails.getReceiptsDetailsId()
							.intValue()) {
						oldItems.remove(receiptsDetails2);
						System.out.println("删除模型oldItems：" + receiptsDetails2);
						break;
					}
				}
			}
		}
		for (ReceiptsDetails receiptsDetails2 : oldItems) {
			int deleteByPrimaryKey = receiptsDetailsMapper.deleteByPrimaryKey(receiptsDetails2.getReceiptsDetailsId());
			System.out.println("删除oldItems数据库条数：" + deleteByPrimaryKey);
		}
		return true;
	}

}