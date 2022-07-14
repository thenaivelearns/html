package com.igw.market.push.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data 
@ApiModel(value = "基础类")
public class BaseInfo implements Serializable {
	
	private static final long serialVersionUID = -1L;

	@ApiModelProperty(name = "validFlag", value = "有效标记(0:有效 1:无效)")
	private String validFlag;
	
	@ApiModelProperty(name = "createdUser", value = "创建人")
	private String createdUser;
	
	@ApiModelProperty(name = "createdDate", value = "创建日期")
	private String createdDate;
	
	@ApiModelProperty(name = "updatedUser", value = "修改人")
	private String updatedUser;
	
	@ApiModelProperty(name = "updatedDate", value = "修改日期")
	private String updatedDate;
	
}
