package com.ljp.process;

/**
 * 默认的处理人策略，为设置时的固定人物
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
