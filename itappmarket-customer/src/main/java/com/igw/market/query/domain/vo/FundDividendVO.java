package com.igw.market.query.domain.vo;

import com.igw.market.query.domain.FundDividend;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分红模板参数")
public class FundDividendVO extends FundDividend {
    @ApiModelProperty(value = "操作类型 1 复核")
    private String operatorType;

}
