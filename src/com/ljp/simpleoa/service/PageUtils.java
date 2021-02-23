package com.ljp.simpleoa.service;

import com.ljp.simpleoa.Constant;
import com.ljp.simpleoa.utils.PageInfo;

public class PageUtils {
	public static void setToPage(PageInfo pageInfo, String toPage) {
		if(pageInfo==null)return ;
		if(toPage!=null) {
			if (toPage.equals(Constant.PAGE_NEXT)) {
				pageInfo.nextPage();
			} else if (toPage.equals(Constant.PAGE_LAST)) {
				pageInfo.lastPage();
			} else {
				try {
					pageInfo.setThisPage(Integer.parseInt(toPage));
				}catch (Exception e) {
					System.out.println("×ª»»Ê§°Ü£¡");
				}
			}
		}
	}
}
