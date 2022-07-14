package com.igw.market.common.service;

import java.util.Map;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.domain.WechatUserInfo;
import com.igw.market.common.dto.MyOperationDto;
import com.igw.market.common.util.PageUtil;

public interface AnnualBillService {

	PageUtil searchList(Integer page, Integer size, Map<String, Object> map);
	
	/**
	 * 用户信息
	 * @param pkSerial
	 * @return
	 */
	WechatUserInfo getWechatUserDetail(String pkSerial);

	/**
	 * 我的年度账单
	 * @param identityId
	 * @param year
	 * @return
	 */
	ResultMessage getMyAnnualBill(String custNo, String year);
	
	 Map<String,Object> getCustNo(String identityId, String idType);

	 /**
	  * 我的月度账单
	  * @param custNo
	  * @param monthTime
	  * @return
	  */
	ResultMessage getMyAnnualMonthBill(String custNo, String firstDay, String lastDay);

}
