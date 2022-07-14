package com.igw.market.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.igw.base.quartz.template.QuartzRamJob;
import com.igw.market.common.util.DateUtil;
import com.igw.market.push.dao.SubscriptionStatisticsDao;
import com.igw.market.push.domain.SubscriptionStatisticsDomain;
import com.igw.market.push.enums.PushEnums;
import com.igw.market.push.enums.PushEnums.PushTypeList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscriptionyJob implements QuartzRamJob {
	
	@Autowired
	private SubscriptionStatisticsDao subscriptionStatisticsDao;
	
	 /**
     * 控制Job运行时间的cron表达式
     */
    @Value("${push.cron.subscriptionyJob}")
    private String CRON_EXPRESSION;

    /**
     * 该Job的运行方法名称
     */
    private static final String EXECUTE_METHOD_NAME = "execute";



    @Override
    public String getCronExpression() {
        return CRON_EXPRESSION;
    }

    @Override
    public String getExecuteMethodName() {
        return EXECUTE_METHOD_NAME;
    }

    public void execute()  {
        log.info(new Date() +"---SubscriptionyJobjob 开始执行!---------");
        // 获取推送类型
        PushTypeList[] lists = PushEnums.PushTypeList.values();
        Map<String, Object> map = new HashMap<String, Object>();
        // 当前日期
        String tdate = DateUtil.getYYYYMMDD(new Date());
        for (PushTypeList pushTypeList : lists) {
        	String key = pushTypeList.getKey();
        	if(StringUtils.isNotBlank(key)) {
        		map.put("pushType", key);
        		map.put("pushValue", '0');
        		// 查询订阅/退订人数
        		int yCount = subscriptionStatisticsDao.selectSubscriptionCounts(map);
        		map.put("pushValue", '1');
        		int nCount = subscriptionStatisticsDao.selectSubscriptionCounts(map);
        		SubscriptionStatisticsDomain subscriptionStatisticsDomain = new  SubscriptionStatisticsDomain();
        		subscriptionStatisticsDomain.setCreatedUser("system");
        		subscriptionStatisticsDomain.setPushType(key);
        		subscriptionStatisticsDomain.setSubscribeCount(String.valueOf(yCount));
        		subscriptionStatisticsDomain.setUnsubscribeCount(String.valueOf(nCount));
        		subscriptionStatisticsDomain.setTdate(tdate);
        		subscriptionStatisticsDao.add(subscriptionStatisticsDomain);
        	}
		}
        log.info(new Date() +"---SubscriptionyJobjob 执行完成!---------");
    }
    

}
