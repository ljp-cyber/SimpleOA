package com.ljp.process;

/**
 * Ĭ�ϵĴ����˲��ԣ�Ϊ����ʱ�Ĺ̶�����
 * @author LiJunPeng
 *
 */
public class DefaultDealerStrategy implements DealerStrategy {

	private Object dealer;
	
	public DefaultDealerStrategy(Object dealer) {
		this.dealer=dealer;
	}
	
	@Override
	public Object getDealer(Object arg) {
		return dealer;
	}

}
