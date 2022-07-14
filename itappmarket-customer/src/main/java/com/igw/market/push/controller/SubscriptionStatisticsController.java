package com.igw.market.push.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.util.DateUtil;
import com.igw.market.common.util.JacksonUtil;
import com.igw.market.push.dao.SubscriptionStatisticsDao;
import com.igw.market.push.domain.SubscriptionStatisticsDomain;
import com.igw.market.push.enums.PushEnums;
import com.igw.market.push.enums.PushEnums.PushTypeList;

import lombok.Data;

/**
 * 订阅统计
 * @author aiyongqiang
 *
 */
@RequestMapping("subscriptionStatistics")
@RestController
public class SubscriptionStatisticsController {
	
	@Autowired
	private SubscriptionStatisticsDao subscriptionStatisticsDao;
		
	/**
	 * 查询当前订阅/退订人数
	 */
	@RequestMapping("getSubscriptionCounts")
	public ResultMessage<List<Map<String, Object>>> getSubscriptionCounts() {
		// 获取推送类型
        PushTypeList[] lists = PushEnums.PushTypeList.values();
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (PushTypeList pushType : lists) {
			Map<String, Object> resultMap = new HashMap<>();
			String key = pushType.getKey();
        	if(StringUtils.isNotBlank(key)) {
        		map.put("pushType", key);
        		map.put("pushValue", '0');
        		// 查询订阅/退订人数
        		int yCount = subscriptionStatisticsDao.selectSubscriptionCounts(map);
        		map.put("pushValue", '1');
        		int nCount = subscriptionStatisticsDao.selectSubscriptionCounts(map);
        		resultMap.put("pushType", key);
        		resultMap.put("pushName", pushType.getName());
        		resultMap.put("yCount", yCount);
        		resultMap.put("nCount", nCount);
        		resultMap.put("tCount", yCount + nCount);
        		resultList.add(resultMap);
        	}
		}
        return ResultMessage.ok(resultList);
	}
	
	/**
	 * 查询历史时间订阅/退订人数
	 */
	@RequestMapping("getHistorySubscriptionCounts")
	public ResultMessage getHistorySubscriptionCounts(@RequestBody String body) {
		String pushType = JacksonUtil.parseString(body, "pushType");
		String pushName = JacksonUtil.parseString(body, "pushName");
		if(StringUtils.isBlank(pushType)) {
			return ResultMessage.fail("订阅类型不能为空");
		}
		String startTime = JacksonUtil.parseString(body, "startTime");
		String endTime = JacksonUtil.parseString(body, "endTime");
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
        	return ResultMessage.fail("筛选日期不能为空");	
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("pushType", pushType);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		
		List<SubscriptionStatisticsDomain>  list = subscriptionStatisticsDao.getSubscriptionList(paramMap);
		if(list != null && list.size() > 0) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("title", pushName);
			// legend data
			String[] legendList = {"总计","订阅","退订"};
			resultMap.put("legendList", legendList);
			// xAxis data
			List<String> xAxisList = new ArrayList<>();
			// series
			List<Map<String, Object>> seriesList = new ArrayList<>();
			Map<String, Object> tMap = new HashMap<>();
			tMap.put("name", "总计");
			tMap.put("type", "line");
			Map<String, Object> yMap = new HashMap<>();
			yMap.put("name", "订阅");
			yMap.put("type", "line");
			List<String> tlist = new ArrayList<>();
			List<String> yList = new ArrayList<>();
			List<String> nList = new ArrayList<>();
			Map<String, Object> nMap = new HashMap<>();
			nMap.put("name", "退订");
			nMap.put("type", "line");
			
			for (SubscriptionStatisticsDomain item : list) {
				xAxisList.add(item.getTdate());
				yList.add(item.getSubscribeCount());
				nList.add(item.getUnsubscribeCount());
				int t = Integer.parseInt(item.getSubscribeCount()) + Integer.parseInt(item.getUnsubscribeCount());
				tlist.add(String.valueOf(t));
			}
			yMap.put("data", yList);
			nMap.put("data", nList);
			tMap.put("data", tlist);
			seriesList.add(tMap);
			seriesList.add(yMap);
			seriesList.add(nMap);
			resultMap.put("xAxisList", xAxisList);
			resultMap.put("seriesList", seriesList);
			return ResultMessage.ok(resultMap);
		} else {
			return ResultMessage.fail("记录暂不存在");
		}
	}
	
}
