package com.igw.market.institutional.domain;

import com.igw.market.push.domain.BaseInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "机构客服交易服务明细")
public class InstitutionalTradeInfo extends BaseInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@ApiModelProperty(value = "主键ID")
	private String pkSerial;
	
	@ApiModelProperty(value = "机构服务代码")
	private String institutionalCode;
	
	@ApiModelProperty(value = "文件说明")
	private String fileExplain;
	
	@ApiModelProperty(value = "文件名称")
	private String fileName;
	
	@ApiModelProperty(value = "文件来源")
	private String fileSouce;
	
	@ApiModelProperty(value = "更名文件名")
	private String newFileName;
	
	@ApiModelProperty(value = "数据接口")
	private String dataInterface;
	
	@ApiModelProperty(value = "数据接口入参")
	private String param;
	
	@ApiModelProperty(value = "模板文件")
	private String templateDBF;
	
	@ApiModelProperty(value = "是否需要空文件 0是，1否")
	private String isNullFile;
	
	@ApiModelProperty(value = "数据方式: 1:接口，2:文件过滤")
	private String dataMode;
	
}
