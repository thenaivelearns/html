package com.igw.market.common.util;

import java.io.Serializable;
import java.util.List;

public class PageUtil<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  总条数
	 */
	private long total;
	
	/**
	 *  集合
	 */
	private List<T> list;

	public long getTotal() {
		return total;
	}

	public List<T> getList() {
		return list;
	}
	
	public PageUtil(long total,  List<T> list) {
		this.total = total;
		this.list = list;
	}

}
