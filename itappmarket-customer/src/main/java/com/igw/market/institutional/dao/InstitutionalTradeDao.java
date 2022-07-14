package com.igw.market.institutional.dao;

import java.util.List;
import java.util.Map;

import com.igw.market.institutional.domain.InstitutionalTrade;
import com.igw.market.institutional.domain.InstitutionalTradeInfo;

public interface InstitutionalTradeDao {

	
	/**
	 * 	获取所有机构客户
	 * @return
	 */
	public List<InstitutionalTrade> getInstitutionalTrade(Map<String, String> map);
	
	/**
	 *	 获取所有机构客户 所需文件 
	 * @return
	 */
	public List<InstitutionalTradeInfo> getInstitutionalTradeInfo(Map<String, String> map);
	
	/**
	 * 新增委托人
	 * @param info
	 * @return
	 */
	public int add(InstitutionalTrade info);

	/**
	 * 修改委托人信息
	 * @param info
	 * @return
	 */
	public int edit(InstitutionalTrade info);
	
	/**
	 * 删除委托人信息
	 * @param info
	 * @return
	 */
	public int delete(InstitutionalTrade info);
}
