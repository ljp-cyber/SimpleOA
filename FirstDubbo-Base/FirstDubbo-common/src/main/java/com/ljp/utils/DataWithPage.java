package com.ljp.utils;

import java.io.Serializable;
import java.util.List;

public class DataWithPage<T> extends PageInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<T> dateSource;

	/**
	 * Ĭ��ÿҳ10���1ҳ��ʼ
	 * 
	 * @param dateSource
	 *            ����Դ
	 */
	public DataWithPage(List<T> dateSource) {
		super( dateSource==null?0:dateSource.size());
		this.dateSource = dateSource;
		if(dateSource==null) {
			rowCount=0;
		}else {
			rowCount=dateSource.size();
		}
		init();
	}

	/**
	 * Ĭ�ϴ�1ҳ��ʼ
	 * 
	 * @param pageSize
	 *            ÿҳ����
	 * @param dateSource
	 *            ����Դ
	 */
	public DataWithPage(List<T> dateSource, int pageSize) {
		super(pageSize, dateSource==null?0:dateSource.size());
		this.dateSource = dateSource;
		if(dateSource==null) {
			rowCount=0;
		}else {
			rowCount=dateSource.size();
		}
		if(pageSize<1) {
			pageSize=1;
		}
		this.pageSize = pageSize;
		init();
	}
	
	public List<T> getPageData() {
		return MyCollenctionUtils.addSomeToList(dateSource, getLimit(), getPageSize());
	}
	
	public void setDateSource(List<T> dateSource) {
		this.dateSource = dateSource;
		if(dateSource==null) {
			rowCount=0;
		}else {
			rowCount=dateSource.size();
		}
		init();
	}
}
