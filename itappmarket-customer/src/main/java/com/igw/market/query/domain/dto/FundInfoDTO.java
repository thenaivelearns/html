package com.igw.market.query.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "基金基础信息返回参数")
public class FundInfoDTO implements Serializable {
    private static final long serialVersionUID = 4651305431721204915L;

    @ApiModelProperty(name = "fundCode", value = "基金代码")
    private String fundCode;

    @ApiModelProperty(name = "fundName", value = "基金简称")
    private String fundName;

    @ApiModelProperty(name = "fundFullName", value = "基金全称")
    private String fundFullName;

    @ApiModelProperty(name = "status", value = "产品运作状态")
    private String status;

    @ApiModelProperty(name = "fundRaiseType", value = "募集类型 1:公募 2专户")
    private String fundRaiseType;
}
