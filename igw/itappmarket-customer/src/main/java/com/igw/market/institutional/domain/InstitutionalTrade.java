package com.igw.market.institutional.domain;

import com.igw.market.push.domain.BaseInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "机构客服交易服务")
public class InstitutionalTrade extends BaseInfo {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键ID")
	private String pkSerial;
	
	@ApiModelProperty(value = "机构客户名称")
	private String institutionalName;
	
	@ApiModelProperty(value = "交易席位")
	private String tradeSeat;
	
	@ApiModelProperty(value = "清算代码")
	private String clearCode;
	
	@ApiModelProperty(value = "上海股东代码")
	private String shStockCode;
	
	@ApiModelProperty(value = "深证股东代码")
	private String szStockCode;
	
	@ApiModelProperty(value = "是否需要打包 0 是 1否")
	private String isZip;
	
	@ApiModelProperty(value = "导出路径")
	private String reportPath;
	
	@ApiModelProperty(value = "导出名称")
	private String reportName;
	
	@ApiModelProperty(value = "邮箱(多个;号分隔)")
	private String email;
	
	
}
