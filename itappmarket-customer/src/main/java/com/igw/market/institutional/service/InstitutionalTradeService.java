package com.igw.market.institutional.service;

import java.util.Map;

import com.igw.market.common.domain.WechatUserInfo;
import com.igw.market.common.util.PageUtil;
import com.igw.market.common.util.ResultMessage;
import com.igw.market.institutional.domain.InstitutionalTrade;

public interface InstitutionalTradeService {
	
	/**
	   *   生成委托人需要的交易文件
	 * @return
	 */
	public ResultMessage createTradeFile(String fileDate);

	/**
	 * w
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	public PageUtil<InstitutionalTrade> searchList(Integer page, Integer size, Map<String, String> map);

	public int addInstitutionalTrade(InstitutionalTrade info);

}
