package com.ljp.simpleoa.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ljp.simpleoa.config.RootConfig;
import com.ljp.simpleoa.model.Receipts;
import com.ljp.simpleoa.model.ReceiptsRecord;
import com.ljp.simpleoa.model.Worker;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
@Rollback
public class Test_Service{
	
	@Autowired
	ReceiptsDaoService receiptsDaoService;
	
	@Test
	public void leftJion_Test() {
		List<Receipts> list = receiptsDaoService.queryReceiptsByCreaterId(4,false);

		System.out.println(list);
	}
	
	@Test
	public void leftJion2_Test() {
		List<ReceiptsRecord> list = receiptsDaoService.queryReceiptsRecordReceiptsId(37);

		System.out.println(list);
	}
	
	@Autowired
	TestTx testTx;
	

	@Test
	public void test() {
		testTx.test();
	}
}
