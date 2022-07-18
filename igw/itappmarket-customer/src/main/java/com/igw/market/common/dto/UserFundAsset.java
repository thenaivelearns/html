package com.igw.market.common.dto;

import lombok.Data;

@Data
public class UserFundAsset {
	
	private String beginDate;
	
	private String endDate;
	private String fundAcco;
	private String fundCode;
	private String sumIncome;
	private String yesterDayIncome;

}
