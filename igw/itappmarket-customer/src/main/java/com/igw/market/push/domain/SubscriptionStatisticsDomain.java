package com.igw.market.push.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 预约统计
 * @author aiyongqiang
 *
 */
@Data 
@ApiModel(value = "预约统计")
public class SubscriptionStatisticsDomain extends BaseInfo{

	private static final long serialVersionUID = 763620815859688571L;
	
	@ApiModelProperty(value = "主键ID")
	private String pkId;
	
	@ApiModelProperty(value = "订阅类型1.实时交易通知2.确认通知3.基金预约4.发售提醒5.直播提醒6.分红信息")
	private String pushType;
	
	@ApiModelProperty(value = "订阅人数")
	private String subscribeCount;

	@ApiModelProperty(value = "退订人数")
	private String unsubscribeCount;
	

	@ApiModelProperty(value = "日期")
	private String tdate;


}
