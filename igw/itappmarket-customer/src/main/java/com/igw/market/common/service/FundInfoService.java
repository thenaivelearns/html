package com.igw.market.common.service;

import java.util.List;

import com.igw.market.common.domain.FundInfo;

/**
 *	 基金信息
 * @author liwen
 *
 */
public interface FundInfoService {

    /**
		 * 通过文件名称获取基金信息
		* @param pkId
		* @return
	*/
	FundInfo getFundInfoByFileName(String fileName);
	
	/**
		* 通获得所有基金
		* @return
	*/	
	List<FundInfo> getFundInfoList();
}
