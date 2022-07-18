package com.igw.market.push.enums;

public class PushEnums {
	
    public enum PushTypeList {
		
    	rtTradeSend ("1","基金交易提醒"),
    	confirmTrade("2","基金交易确认通知"),
    	appointment ("3","新基金开售订阅通知"),
    	fundSale    ("4","基金发售通知"),
    	showFund    ("5","直播路演通知"),
    	dividendsRemind    ("6","直销客户基金交易确认结果通知"),
    	dividendsAssign    ("7","分红分配通知"),
    	dividendsReport    ("8","业务提醒");

		String key;
		String name;

		PushTypeList(String key, String name) {
			this.key = key;
			this.name = name;
		}

		public String getKey() {
			return key;
		}

		public String getName() {
			return name;
		}
	}

}
