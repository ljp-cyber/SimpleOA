package com.ljp.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import com.ljp.bean.Receipts;
import com.ljp.bean.Worker;
import com.ljp.serviceInterface.ReceiptsServiceFace;

/**
 * 用于过滤器14里授权页面的投票权
 * @author LiJunPeng
 *
 */
//@Component
public class MyRoleVoter implements AccessDecisionVoter<Object> {

	private static final int CREATER=0;
	private static final int DEALER=1;
	private static final int CREATER_AND_DEALER=2;

	Map<String, Integer> map;

	@Autowired
	private ReceiptsServiceFace receiptsService;

	public MyRoleVoter() {
		System.out.println("MyRoleVoter.constructor");
		map = new HashMap<String, Integer>();
		map.put("/receipts/toCheck", DEALER);
		map.put("/receipts/check", DEALER);
		map.put("/receipts/submit", CREATER);
		map.put("/receipts/detail", CREATER_AND_DEALER);
		map.put("/receipts/toUpdate", CREATER);
		map.put("/receipts/update", CREATER);
		map.put("/receipts/remove", CREATER);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("MyRoleVoter.supports.Class:" + clazz);
		return true;
	}
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		System.out.println("MyRoleVoter.supports.ConfigAttribute:" + attribute);
		System.out.print(attribute.getAttribute());
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		FilterInvocation fi = (FilterInvocation) object;
		HttpSession httpSession = fi.getRequest().getSession();
		String url = fi.getRequestUrl();
		System.out.println(url);
		if(map.get(url)==null) {
			return ACCESS_ABSTAIN;
		}
		System.out.println("MyRoleVoter.authentication:"+authentication);
		if (authentication.getName() == null || authentication.getName().isEmpty()) {
			return ACCESS_DENIED;
		}
		Worker user = (Worker) httpSession.getAttribute("user");
		System.out.println("MyRoleVoter.user:" + user);
		if (user == null) {
			return ACCESS_DENIED;
		}
		HttpServletRequest httpRequest = fi.getHttpRequest();
		String str = httpRequest.getParameter("receiptsId");
		Integer receiptsId = Integer.parseInt(str);
		System.out.println("MyRoleVoter.receiptsId:" + receiptsId);
		
		if (receiptsId != null) {
			Receipts receipts = receiptsService.queryReceiptsByPK(receiptsId, false);
			if(receipts==null){
				return ACCESS_DENIED;
			}
			switch (map.get(url)) {
			case CREATER:
				if (receipts.getCreaterId().equals(user.getWorkerId())) {
					return ACCESS_GRANTED;
				}
				break;
			case DEALER:
				if (receipts.getNextDealId().equals(user.getWorkerId())) {
					return ACCESS_GRANTED;
				}
				break;
			case CREATER_AND_DEALER:
				if (receipts.getCreaterId().equals(user.getWorkerId())) {
					return ACCESS_GRANTED;
				}else if (receipts.getNextDealId().equals(user.getWorkerId())) {
					return ACCESS_GRANTED;
				}
				break;
			default:
				break;
			}
			
		}
		return ACCESS_DENIED;
	}

}
