package com.igw.market.common.domain;

import com.igw.market.push.domain.BaseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhaotd
 * @date 2021/9/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "投资黄历表")
public class AlmanacInfo extends BaseInfo {

    @ApiModelProperty(value = "主键id")
    private String pkId;

    @ApiModelProperty(value = "公告日期 yyyyMMdd",required = true)
    private String informationDate;

    @ApiModelProperty(value = "公告时间 hh:mm",required = true)
    private String informationTime;

    @ApiModelProperty(value = "标题",required = true)
    private String title;

    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @ApiModelProperty(value = "基金编码")
    private String fundCode;

    @ApiModelProperty(value = "基金名称")
    private String fundName;

    @ApiModelProperty(value = "公告类别编码",required = true)
    private String noticeCode;

    @ApiModelProperty(value = "公告类别名称",required = true)
    private String noticeName;
    
    @ApiModelProperty(value = "公告ID",required = true)
    private String noticeId;

}
