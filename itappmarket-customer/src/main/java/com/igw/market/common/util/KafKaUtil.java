package com.igw.market.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import com.alibaba.fastjson.JSONArray;

public class KafKaUtil {
	
	private static Logger logger = Logger.getLogger(KafKaUtil.class);
	
	
	public static Map<String, Object> kafkaPah(Map<String,Object>  jsonMap ,KafkaTemplate kafkaTemplate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 4.存入kafka，返回成功失败标识
		//异步
		List<String> errMsg=new ArrayList<String>();								
		ListenableFuture result = kafkaTemplate.send("send_apply_msg", JSONArray.toJSON(jsonMap).toString());
			// 发送成功后回调
			SuccessCallback successCallback = new SuccessCallback() {
				@Override
				public void onSuccess(Object result) {
					logger.info(JSONArray.toJSON(jsonMap)+"kafka发送成功");
				}
			};
			// 发送失败回调
			FailureCallback failureCallback = new FailureCallback() {
				@Override
				public void onFailure(Throwable ex) {
					logger.info(JSONArray.toJSON(jsonMap)+"kafka发送失败");
					errMsg.add("推送失败");
				}
			};
			result.addCallback(successCallback,failureCallback);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				logger.error("sleep()暂停线程异常", e);
				resultMap.put("code", "1");
				resultMap.put("msg", "微信推送失败");
			}
			if(CollectionUtils.isEmpty(errMsg)) {
				resultMap.put("code", "0");
				resultMap.put("msg", "微信推送成功");
			}else {
				resultMap.put("code", "1");
				resultMap.put("msg", "微信推送失败"+errMsg.toString());
			}
			logger.info("微信推送kafka，推送内容="+ JSONArray.toJSON(jsonMap));
		
		return resultMap;
	}
}
