package com.igw.market.push.vo;

import com.igw.market.common.domain.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: DividendNoticeVO
 * Description： TODO
 * Author: zhubengang
 * Date: Created in 2021/8/18 9:56
 * Version: 1.0.0
 */
@ApiModel(value = "分红通知请求参数")
@Data
public class DividendNoticeVO extends PageParam {

    @ApiModelProperty(name = "noticeName",value = "公告名称")
    private String noticeName;
}
