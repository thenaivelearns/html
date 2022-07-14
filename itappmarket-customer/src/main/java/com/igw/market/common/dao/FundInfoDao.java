package com.igw.market.common.dao;

import java.util.List;

import com.igw.market.common.domain.FundInfo;

public interface FundInfoDao {
	
	/**
	 * 获取所有基金
	 * @return
	 */
	List<FundInfo> getFundInfo();
}
