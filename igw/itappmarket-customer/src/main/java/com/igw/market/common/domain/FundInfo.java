package com.igw.market.common.domain;

public class FundInfo {

	
	// 基金代码
	private String fundCode;
	// 基金全称
	private String fundName;
	// 基金简称
	private String fundShortName;
	// 基金经理 
	private String fundManager;
	// 基金关键字
	private String keyword;
	
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getFundShortName() {
		return fundShortName;
	}
	public void setFundShortName(String fundShortName) {
		this.fundShortName = fundShortName;
	}
	public String getFundManager() {
		return fundManager;
	}
	public void setFundManager(String fundManager) {
		this.fundManager = fundManager;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
