package com.igw.market.common.domain;

import com.igw.market.push.domain.BaseInfo;

import lombok.Data;

@Data
public class AnnualBillInfo extends BaseInfo {

	private String pkId;
	// 身份证
	private String custno;
	// 年份 yyyy
	private String tDate;
	// 状态 Y 成功 N 失败
	private String cstate;
	// 账单信息
	private String bill;
	// 描述
	private String msgdesc;
}
