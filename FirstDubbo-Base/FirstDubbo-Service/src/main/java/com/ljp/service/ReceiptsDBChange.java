package com.ljp.service;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ReceiptsDBChange {
	
	public ReceiptsDBChange() {
		System.out.println("ReceiptsDBChange����");
	}
	
	public static boolean change=true;
	@AfterReturning("execution(* com.ljp.simpleoa.service.ReceiptsDaoService.add*(..)) "
			+ "|| execution(* com.ljp.simpleoa.service.ReceiptsDaoService.update*(..))")
	public void monitor() {
		System.out.println("����֪ͨ��⵽���ݿ���ģ�����");
		change= true;
	}

}
