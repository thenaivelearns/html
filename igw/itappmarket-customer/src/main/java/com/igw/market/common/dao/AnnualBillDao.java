package com.igw.market.common.dao;

import java.util.List;
import java.util.Map;

import com.igw.market.common.domain.AnnualBillInfo;
import com.igw.market.common.domain.WechatUserInfo;
import com.igw.market.common.dto.UserFundAsset;

public interface AnnualBillDao {

	List<WechatUserInfo> searchList(Map<String, Object> map);
	
	WechatUserInfo getWechatUserDetail(String pkSer);

	/**
	 * 操作过的基金
	 * @param map
	 * @return
	 */
	List<String> getOperationFundCodes(Map<String, Object> map);

	/**
	 * 操作次数
	 * @param map
	 * @return
	 */
	int getOperationCounts(Map<String, Object> map);
	
	UserFundAsset getUserAssetAndProfit(Map<String,Object> map);

	/**
	 * 通过证件获取客户号
	 * @param identityId
	 * @return
	 */
	Map<String,Object> getCustNo(Map<String,Object> map);

	/**
	 * 通过客户号获取基金账户
	 * @param identityId
	 * @return
	 */
	List<String> getFundAccoByCustNo(String identityId);

	/**
	 * 资产
	 * @param map
	 * @return
	 */
	String getPersonalAssets(Map<String, Object> map);

	/**
	 * 持有基金
	 * @param assmMap
	 * @return
	 */
	List<Map<String, Object>> getHoldFundDetail(Map<String, Object> assmMap);

	/**
	 * 创建账单
	 * @param annualBillInfo
	 * @return
	 */
	int addBill(AnnualBillInfo annualBillInfo);
	
	/**
	 * 获取我的年度账单
	 * @param assmMap
	 * @return
	 */
	List<AnnualBillInfo> getMyAnnualBill(Map<String, Object> assmMap);

	/**
	 * 获取基金持有过的基金
	 * @param map
	 * @return
	 */
	List<Map<String, String>> getFundByAcco(Map<String, Object> map);

	/**
	 * 基金收益情况
	 * @param map
	 */
	void getSingleiiyinfo(Map<String, Object> map);

	void fcCustiiDetailinfox(Map<String, Object> detailinfoxMap);

	String getFundNameByCode(String fundcode);

    String getProperty(String custNo);
    /**
     * 通过基金账号获取客户号
     * @return
     */
    Map<String, Object> getCustNoByFundacco(String fundacco);

    /**
     * 资产明细
     * @param assmMap
     * @return
     */
	List<Map<String, Object>> getAssetDetailsList(Map<String, Object> assmMap);
	
	/**
	 * 
	 * @param detailinfoxMap
	 * @return
	 */
	List<Map<String, Object>> changesAssets(Map<String, Object> detailinfoxMap);

	/**
	 * 根据客户编号查询浮动盈亏数据(DCT)
	 * @param detailinfoxMap
	 */
	void getfloatingprofitlosscust(Map<String, Object> detailinfoxMap);
}
