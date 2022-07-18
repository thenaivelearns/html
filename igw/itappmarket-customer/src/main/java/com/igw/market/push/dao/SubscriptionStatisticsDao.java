package com.igw.market.push.dao;

import java.util.List;
import java.util.Map;

import com.igw.market.push.domain.SubscriptionStatisticsDomain;

public interface SubscriptionStatisticsDao {
	
	/**
	 * 新增
	 * @param subscriptionStatisticsDomain
	 * @return
	 */
	int add(SubscriptionStatisticsDomain subscriptionStatisticsDomain);

	/**
	 * 查询订阅/退订人数
	 * @param map
	 * @return
	 */
	int selectSubscriptionCounts(Map<String, Object> map);

	/**
	 * 订阅记录
	 * @param paramMap 
	 * @return
	 */
	List<SubscriptionStatisticsDomain> getSubscriptionList(Map<String, Object> paramMap);

}
