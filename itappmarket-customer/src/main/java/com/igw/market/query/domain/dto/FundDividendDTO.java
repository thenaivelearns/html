package com.igw.market.query.domain.dto;

import com.igw.market.query.domain.FundDividend;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: FundDividendDTO
 * Description： TODO
 * Author: zhubengang
 * Date: Created in 2021/8/27 9:09
 * Version: 1.0.0
 */
@Data
public class FundDividendDTO extends FundDividend {

    @ApiModelProperty(value = "是否显示复核 1 显示 2 不显示")
    private String isShowCheck;
}
