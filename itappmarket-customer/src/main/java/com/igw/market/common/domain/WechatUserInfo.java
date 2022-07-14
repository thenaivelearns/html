package com.igw.market.common.domain;

import lombok.Data;

@Data
public class WechatUserInfo {
	
	// 主键
	private String pkSerial;
	//
	private String openId;
	// 基金账户
	private String fundAcco;
	// 用户名
	private String userName;
	// 手机号
	private String userMobile;
	// 邮箱
	private String userEmail;
	// 身份证
	private String identityId;
	
	private String identitytype;

}
