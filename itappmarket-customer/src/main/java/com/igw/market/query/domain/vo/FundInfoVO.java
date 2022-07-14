package com.igw.market.query.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "参数条件：基金基础信息请求参数")
public class FundInfoVO {
    @ApiModelProperty(value = "查询参数：基金代码", required = false, example = "10004,10005")
    private String fundCodes;
    @ApiModelProperty(value = "查询参数：基金名称", required = false, example = "")
    private String fundName;
    @ApiModelProperty(value = "查询参数：公募/专户（1-公募，2-专户）", required = false, example = "1,2")
    private String fundRaiseType;
    @ApiModelProperty(value = "查询参数：产品运作状态（00-未成立，10-运作中，20-清盘中，30-已清盘，40-募集失败）", required = false, example = "00,10,20,30,40")
    private String status;
}