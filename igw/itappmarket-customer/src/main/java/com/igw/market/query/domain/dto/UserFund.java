package com.igw.market.query.domain.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 基金信息
 */
@Data
@Api(value = "用户基金信息")
public class UserFund {
    @ApiModelProperty(name = "userName", value = "用户名称")
    private String userName;
    @ApiModelProperty(name = "openId",value = "微信用户唯一标识")
    private String openId;
    @ApiModelProperty(name = "custNo", value = "客户号")
    private String custNo;
    @ApiModelProperty(name = "identityNo", value = "身份证号")
    private String identityNo;
    @ApiModelProperty(name = "identityType", value = "证件类型")
    private String identityType;
    @ApiModelProperty(name = "fundCode", value = "基金代码")
    private String fundCode;
    @ApiModelProperty(name = "fundName", value = "基金名称")
    private String fundName;
    @ApiModelProperty(name = "realshares", value = "份额")
    private BigDecimal realshares;
    @ApiModelProperty(name = "netValue", value = "净值")
    private BigDecimal netValue;
    @ApiModelProperty(name = "holdingAmount", value = "持有金额")
    private BigDecimal holdingAmount;
    @ApiModelProperty(name = "wechatCode", value = "微信用户环境")
    private String wechatCode;
//    (a.F_REALSHARES*t.F_NETVALUE)


}
