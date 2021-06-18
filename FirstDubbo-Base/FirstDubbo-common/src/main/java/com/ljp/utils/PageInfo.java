package com.ljp.utils;

import java.io.Serializable;

public class PageInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "共"+rowCount+"项，第"+thisPage+"/"+pageCount+"页";
	}

	protected int pageSize = 10;
	protected int thisPage = 1;
	protected int rowCount;
	protected int pageCount;

	/**
	 * 默认每页10项，从1页开始
	 * 
	 * @param rowCount
	 *            总项数
	 */
	public PageInfo(int rowCount) {
		this.rowCount = rowCount;
		init();
	}

	/**
	 * 默认从1页开始
	 * 
	 * @param pageSize
	 *            每页项数
	 * @param rowCount
	 *            总项数
	 */
	public PageInfo(int pageSize, int rowCount) {
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		init();
	}

	public int getLimit() {
		return (thisPage - 1) * pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getThisPage() {
		return thisPage;
	}

	protected void init() {
		pageCount = (rowCount / pageSize) + (rowCount % pageSize == 0 ? 0 : 1);
	}

	public int lastPage() {
		thisPage--;
		if (thisPage < 1) {
			thisPage = 1;
		}
		else if (thisPage > pageCount) {
			thisPage = pageCount;
		}
		return thisPage;
	}

	public int nextPage() {
		thisPage++;
		if (thisPage < 1) {
			thisPage = 1;
		}
		else if (thisPage > pageCount) {
			thisPage = pageCount;
		}
		return thisPage;
	}

	public void setPageSize(int pageSize) {
		if(pageSize<1) {
			pageSize=1;
		}
		this.pageSize = pageSize;
		init();
	}
	
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		init();
	}
	
	public int setThisPage(int thisPage) {
		if (thisPage < 1) {
			thisPage = 1;
		}
		else if (thisPage > pageCount) {
			thisPage = pageCount;
		}
		this.thisPage = thisPage;
		return thisPage;
	}

}
