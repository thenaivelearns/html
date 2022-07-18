package com.igw.market.common.dto;

import java.util.List;

import lombok.Data;

/**
 * 我的操作
 * @author aiyongqiang
 *
 */
@Data
public class MyOperationDto {

	/**
	 * 买过的基金次数
	 */
	private int fundCodes;
	
	/**
	 * 操作次数
	 */
	private int operationCounts;
	
	/**
	 * 现金宝买基金次数
	 */
	private int nowJinbaoCounts;
	
	private Boolean isDirectSelling;
	
}
